package org.rnd.jmagic.engine;

/**
 * Provides a method for matching and grouping damage into triggerable batches.
 */
public interface DamagePattern
{
	public java.util.Set<DamageAssignment.Batch> match(DamageAssignment.Batch damage, Identified thisObject, GameState state);
}
