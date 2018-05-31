package phone.com

/**
  */
trait CustomerCharges {

  val shortCalls: PartialFunction[Long, BigDecimal] = {
    case duration if (duration.compareTo(180)) == -1 => BigDecimal(duration) * 0.05
  }

  val longCalls: PartialFunction[Long, BigDecimal] = {
    case duration if (duration.compareTo(180)) > -1 => BigDecimal(duration) * 0.03
  }

  val charges = calcWithFallback(shortCalls, longCalls)


  def calculateDaily[T](input: List[T])
                       (calculatableTrns: T => Long)
                       (chargesFunc: PartialFunction[Long, BigDecimal]) = {
    val (maxDuration, calculated) = input.
      map(calculatableTrns(_)).
      foldLeft((0L, BigDecimal(0)))((accm, next) => {
        (math.max(accm._1, next), accm._2 + chargesFunc(next))
      })

    calculated - chargesFunc(maxDuration)
  }

  def calcWithFallback[T, S](condCalc: PartialFunction[T, S], fallback: PartialFunction[T, S]) = {
    condCalc orElse (fallback)
  }


}
