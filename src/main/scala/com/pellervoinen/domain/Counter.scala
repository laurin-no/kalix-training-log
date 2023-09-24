package com.pellervoinen.domain

import com.google.protobuf.empty.Empty
import com.pellervoinen
import kalix.scalasdk.valueentity.ValueEntity
import kalix.scalasdk.valueentity.ValueEntityContext

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

class Counter(context: ValueEntityContext) extends AbstractCounter {
  override def emptyState: CounterState =
    throw new UnsupportedOperationException("Not implemented yet, replace with your empty entity state")

  override def increase(currentState: CounterState, increaseValue: pellervoinen.IncreaseValue): ValueEntity.Effect[Empty] =
    effects.error("The command handler for `Increase` is not implemented, yet")

  override def decrease(currentState: CounterState, decreaseValue: pellervoinen.DecreaseValue): ValueEntity.Effect[Empty] =
    effects.error("The command handler for `Decrease` is not implemented, yet")

  override def reset(currentState: CounterState, resetValue: pellervoinen.ResetValue): ValueEntity.Effect[Empty] =
    effects.error("The command handler for `Reset` is not implemented, yet")

  override def getCurrentCounter(currentState: CounterState, getCounter: pellervoinen.GetCounter): ValueEntity.Effect[pellervoinen.CurrentCounter] =
    effects.error("The command handler for `GetCurrentCounter` is not implemented, yet")

}

