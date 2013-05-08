package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Fortify extends Keyword
{
	private final String cost;

	public Fortify(GameState state, String cost)
	{
		super(state, "Fortify " + cost);
		this.cost = cost;
	}

	@Override
	public Fortify create(Game game)
	{
		return new Fortify(game.physicalState, this.cost);
	}

	@Override
	protected java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();

		ret.add(new FortifyAbility(this.state, this.cost));

		return ret;
	}

	public static final class FortifyAbility extends ActivatedAbility
	{
		private final String cost;

		public FortifyAbility(GameState state, String cost)
		{
			super(state, cost + ": Attach to target land you control. Play this ability only any time you could play a sorcery.");

			this.cost = cost;

			this.setManaCost(new ManaPool(cost));

			SetGenerator landsInPlay = Intersect.instance(InZone.instance(Battlefield.instance()), HasType.instance(Type.LAND));
			Target target = this.addTarget(Intersect.instance(ControlledBy.instance(ControllerOf.instance(ABILITY_SOURCE_OF_THIS)), landsInPlay), "target land you control");
			this.addEffect(attach(ABILITY_SOURCE_OF_THIS, targetedBy(target), "Attach this fortification to target land you control."));

			this.activateOnlyAtSorcerySpeed();
		}

		@Override
		public FortifyAbility create(Game game)
		{
			return new FortifyAbility(game.physicalState, this.cost);
		}
	}
}
