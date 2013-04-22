package org.rnd.jmagic.engine;

/**
 * Creates a replacement effect that will prevent some amount of damage
 */
public class DamagePreventionShield extends DamageReplacementEffect
{
	private SetGenerator target;

	/**
	 * Constructs a single prevention shield for anything in target
	 * 
	 * @param game The game this shield exists within
	 * @param target The objects to shield
	 */
	public DamagePreventionShield(Game game, SetGenerator target, int N)
	{
		super(game, "Prevent the next " + N + " damage that would be dealt to target creature or player this turn");
		this.makePreventionEffect();
		this.target = target;
	}

	/**
	 * @param context The event causing this damage
	 * @param damageAssignments All potentially matchable damage assignments
	 * @return A batch containing all damage directed at any one object in
	 * this.target
	 */
	@Override
	public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
	{
		DamageAssignment.Batch batch = new DamageAssignment.Batch();

		Set targets = this.target.evaluate(context.game, this.getSourceObject(context.state));
		for(DamageAssignment assignment: damageAssignments)
			if(targets.contains(context.state.get(assignment.takerID)))
				batch.add(assignment);

		return batch;
	}

	@Override
	public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
	{
		if(!damageAssignments.isEmpty())
		{
			this.getFloatingContinuousEffect(this.game.physicalState).damage -= damageAssignments.size();
			damageAssignments.clear();
		}

		return new java.util.LinkedList<EventFactory>();
	}
}
