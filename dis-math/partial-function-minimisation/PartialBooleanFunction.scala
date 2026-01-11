import java.lang.Long.bitCount

final case class PartialBooleanFunction(
    arity: Int,
    on: Set[Long],
    off: Set[Long]
):
  require(arity >= 1 && arity <= 63, "arity must be in 1..63")
  require(on.intersect(off).isEmpty, "on and off must be disjoint")
  private val fullMask: Long = (1L << arity) - 1L
  require(
    (on ++ off).forall(v => (v & ~fullMask) == 0L),
    s"values exceed arity=$arity"
  )

  // Это не нужно для решения задачи, но я хочу описать функции взаимодействия с частичной функцией.
  def addTrue(v: Long): PartialBooleanFunction =
    copy(arity, on = on + v, off = off - v)
  def addFalse(v: Long): PartialBooleanFunction =
    copy(arity, on = on - v, off = off + v)
  def remove(v: Long): PartialBooleanFunction =
    copy(arity, on = on - v, off = off - v)

  def isTrue(v: Long): Boolean = on.contains(v)
  def isFalse(v: Long): Boolean = off.contains(v)
  def isDC(v: Long): Boolean = !isTrue(v) && !isFalse(v)

  def minimize(): Set[Cube] =
    val dc = ((0L until (1L << arity)).toSet -- on -- off)
    if on.isEmpty then Set.empty
    else
      require(
        on.size <= 64,
        s"Too many minterms (${on.size}). This impl expects <= 64"
      )

      // Построим список всех точек функции (булевых векторов) и упорядочим их по неубыванию числа единиц веса.
      // Упорядочивание происходит чуть ниже, в рамках iterate
      val startCubes: Set[Cube] = (on ++ dc).map(v => Cube(fullMask, v))

      @annotation.tailrec
      def iterate(current: Set[Cube], acc: Set[Cube] = Set.empty): Set[Cube] =
        // Разобъем список на подмножества (классы) векторов одинакового веса.
        val grouped = current
          .groupBy(c => bitCount(c.value & c.mask))
          .withDefaultValue(Set.empty)
        // Выполним неполные склеивания всех соседних векторов классов. Участвующие в склеивания векторы отметим, а полученные векторы занесем в новый список и приведем в нем подобные.
        val pairings = grouped.keys.toList.sorted.flatMap {
          k => // <-- вот тут происходит упорядочивание
            for
              x <- grouped(k).toList
              y <- grouped.getOrElse(k + 1, Set.empty).toList
              z <- x.combine(y)
            yield (x, y, z)
        }

        // Если новый список векторов не пуст, повторим итерацию
        if pairings.isEmpty then acc ++ current
        else
          val next = pairings.map(_._3).toSet
          val removed = pairings.foldLeft(Set.empty[Cube]) {
            case (s, (x, y, _)) => s + x + y
          }
          val newAcc =
            acc ++ (current -- removed) // Добавляем только непомеченные векторы
          iterate(next, newAcc)

      val onIndexed =
        on.toVector.zipWithIndex // Для того чтобы гарантировать неизменный порядок на время flatmap
      val fullCoverMask =
        if onIndexed.size == 64 then ~0L else (1L << onIndexed.size) - 1L

      // Оставляем только действительно покрывающие хотя бы один вектор простые импликанты
      // Для каждого строим маску - отмечаем все минтермы которые он покрывает
      val primeWithCover: Vector[(Cube, Long)] =
        iterate(startCubes)
          .filter(c => on.exists(c.covers) && off.forall(om => !c.covers(om)))
          .toVector
          .flatMap { p =>
            val mask = onIndexed.foldLeft(0L) { case (acc, (minterm, idx)) =>
              if p.covers(minterm) then acc | (1L << idx) else acc
            }
            if mask != 0L then Some((p, mask)) else None
          }

      def extractEssentials(
          prs: Vector[(Cube, Long)],
          remaining: Long
      ): (Vector[(Cube, Long)], Set[Cube], Long) =
        val counts: Vector[Int] =
          (0 until onIndexed.size).map { i =>
            val bit = 1L << i
            prs.count { case (_, pmask) => (pmask & bit) != 0L }
          }.toVector

        val essentials: Set[Cube] =
          (0 until onIndexed.size).foldLeft(Set.empty[Cube]) { (acc, i) =>
            val bit = 1L << i
            if (remaining & bit) != 0L && counts(i) == 1 then
              acc + prs.find { case (_, pmask) => (pmask & bit) != 0L }.get._1
            else acc
          }

        if essentials.isEmpty then (prs, Set.empty, remaining)
        else
          val maskMap = prs.toMap
          val removeMask =
            essentials.foldLeft(0L)((acc, c) => acc | maskMap.getOrElse(c, 0L))
          val newRemaining = remaining & ~removeMask
          val newPrs = prs.filterNot { case (c, _) => essentials(c) }
          (newPrs, essentials, newRemaining)

      // Извлечём (циклически) все существенные простые импликанты
      @annotation.tailrec
      def extractAllEssentials(
          prs: Vector[(Cube, Long)],
          rem: Long,
          acc: Set[Cube]
      ): (Vector[(Cube, Long)], Set[Cube], Long) =
        val (nprs, found, nrem) = extractEssentials(prs, rem)
        if found.isEmpty then (nprs, acc, rem)
        else extractAllEssentials(nprs, nrem, acc ++ found)

      val (primesFiltered, essentials, remainingAfterEss) =
        extractAllEssentials(primeWithCover, fullCoverMask, Set.empty)

      if remainingAfterEss == 0L then essentials
      else
        val sorted: Vector[(Cube, Long)] = primesFiltered.sortBy { (_, mask) =>
          bitCount(mask)
        }.toVector
        val maxCoverSize =
          if sorted.isEmpty then 0 else sorted.map(_._2).map(bitCount).max
        // Рекурсивно перебираем остальные импликанты, чтобы найти минимальное покрытие
        def search(
            idx: Int,
            covered: Long,
            chosen: Vector[Int]
        ): Option[Vector[Int]] =
          if covered == fullCoverMask then Some(chosen)
          else if idx >= sorted.length then None
          else
            val remainingBits = fullCoverMask & ~covered
            val lowerBound =
              if maxCoverSize == 0 then Int.MaxValue
              else (bitCount(remainingBits) + maxCoverSize - 1) / maxCoverSize

            val takeOpt =
              search(idx + 1, covered | sorted(idx)._2, chosen :+ idx)
            val bestAfterTake = takeOpt.map(_.size).getOrElse(Int.MaxValue)
            if chosen.size + lowerBound >= bestAfterTake then takeOpt
            else
              val skipOpt = search(idx + 1, covered, chosen)
              (takeOpt, skipOpt) match
                case (None, None)       => None
                case (Some(t), None)    => Some(t)
                case (None, Some(s))    => Some(s)
                case (Some(t), Some(s)) =>
                  Some(if t.size <= s.size then t else s)

        val initialCoveredByEssentials = essentials.foldLeft(0L)((acc, c) =>
          acc | primesFiltered.toMap.getOrElse(c, 0L)
        )
        val solutionIndicesOpt =
          search(0, initialCoveredByEssentials, Vector.empty)
        val chosenPrimes = solutionIndicesOpt.fold(Set.empty[Cube]) { idxVec =>
          idxVec.map(i => sorted(i)._1).toSet
        }

        essentials ++ chosenPrimes

object PartialBooleanFunction:
  def empty: PartialBooleanFunction =
    PartialBooleanFunction(1, Set.empty, Set.empty)

  def apply(
      vecTrue: Iterable[Long],
      vecFalse: Iterable[Long]
  ): PartialBooleanFunction =
    val on = vecTrue.toSet
    val off = vecFalse.toSet
    if on.isEmpty && off.isEmpty then
      PartialBooleanFunction(1, Set.empty, Set.empty)
    else
      val mx = (on ++ off).max
      val arity = math.max(1, 64 - java.lang.Long.numberOfLeadingZeros(mx))
      PartialBooleanFunction(arity, on, off)

final case class Cube(mask: Long, value: Long):
  def covers(minterm: Long): Boolean = (minterm & mask) == value

  def combine(other: Cube): Option[Cube] =
    if mask != other.mask then None
    else
      val diff = (value ^ other.value) & mask
      if bitCount(diff) == 1 then
        val newMask = mask & ~diff
        val newValue = value & newMask
        Some(Cube(newMask, newValue))
      else None

  def bits: Int = bitCount(mask)

  def toString(arity: Int = 64) =
    (0 until arity).reverse
      .map(i =>
        (((mask >> i) & 1), ((value >> i) & 1)) match
          case (0, _) => '-'
          case (1, 0) => '0'
          case (1, 1) => '1'
          case (_, _) => throw new Exception("Unreachable pattern")
      )
      .mkString

@main def runExample(): Unit =
  val arity = 4
  val on1 = List(0b0000L, 0b0101L, 0b0111L, 0b1011L, 0b1101L, 0b1110L, 0b1111L)
  val off1 = ((0L until (1L << arity)).toSet -- on1.toSet)
  val pbf1 = PartialBooleanFunction(on1, off1)
  val primes1 = pbf1.minimize()
  primes1.foreach(c => println(c.toString(arity)))
  // верный набор 0000, 1-11, 111-, -1-1
  println("----")
  val on2 = Set(0b001L, 0b111L)
  val off2 = Set(0b011L, 0b101L)
  val pbf2 = PartialBooleanFunction(arity = 3, on = on2, off = off2)
  val primes2 = pbf2.minimize()
  primes2.foreach(c => println(c.toString(3)))
  // верный набор 00- 11-
  println("----")
  val on3  = Set(0b001L, 0b111L)                 
  val off3 = Set(0b000L, 0b010L)          
  val dc3  = ((0L until (1L << 3)).toSet -- on3 -- off3)
  val pbf3 = PartialBooleanFunction(arity = 3, on = on3, off = off3)
  val primes3 = pbf3.minimize()
  primes3.foreach(c => println(c.toString(3)))
  // верный набор -01 
