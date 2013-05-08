package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ricochet Trap")
@Types({Type.INSTANT})
@SubTypes({SubType.TRAP})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class RicochetTrap extends Card
{
	/**
	 * IDs of who cast blue spells
	 */
	public static final class BlueSpellsCast extends Tracker<java.util.Collection<Integer>>
	{
		private java.util.HashSet<Integer> value = new java.util.HashSet<Integer>();
		private java.util.Collection<Integer> unmodifiable = java.util.Collections.unmodifiableSet(this.value);

		@Override
		@SuppressWarnings("unchecked")
		public BlueSpellsCast clone()
		{
			BlueSpellsCast ret = (BlueSpellsCast)super.clone();
			ret.value = (java.util.HashSet<Integer>)this.value.clone();
			ret.unmodifiable = java.util.Collections.unmodifiableSet(ret.value);
			return ret;
		}

		@Override
		protected java.util.Collection<Integer> getValueInternal()
		{
			return this.unmodifiable;
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			if(event.type != EventType.BECOMES_PLAYED)
				return false;

			GameObject played = event.parametersNow.get(EventType.Parameter.OBJECT).evaluate(state, null).getOne(GameObject.class);
			return played.getColors().contains(Color.BLUE);
		}

		@Override
		protected void reset()
		{
			this.value.clear();
		}

		@Override
		protected void update(GameState state, Event event)
		{
			Player caster = event.parametersNow.get(EventType.Parameter.PLAYER).evaluate(state, null).getOne(Player.class);
			this.value.add(caster.ID);
		}
	}

	public static final class OpponentCastABlueSpellThisTurn extends SetGenerator
	{
		private static SetGenerator _instance = null;

		public static SetGenerator instance()
		{
			if(_instance == null)
				_instance = new OpponentCastABlueSpellThisTurn();
			return _instance;
		}

		private OpponentCastABlueSpellThisTurn()
		{
			// singleton
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Set opponents = OpponentsOf.get(state, ((GameObject)thisObject).getController(state));

			java.util.Collection<Integer> trackerValue = state.getTracker(BlueSpellsCast.class).getValue(state);
			for(int id: trackerValue)
				if(opponents.contains(state.<Player>get(id)))
					return NonEmpty.set;

			return Empty.set;
		}
	}

	public RicochetTrap(GameState state)
	{
		super(state);

		// If an opponent cast a blue spell this turn, you may pay (R) rather
		// than pay Ricochet Trap's mana cost.
		state.ensureTracker(new BlueSpellsCast());
		SetGenerator oppCastBlue = OpponentCastABlueSpellThisTurn.instance();
		this.addAbility(new org.rnd.jmagic.abilities.Trap(state, this.getName(), oppCastBlue, "If an opponent cast a blue spell this turn", "(R)"));

		// Change the target of target spell with a single target.
		Target target = this.addTarget(Intersect.instance(Spells.instance(), HasOneTarget.instance()), "target spell with a single target");

		EventFactory swerve = new EventFactory(EventType.CHANGE_TARGETS, "Change the target of target spell with a single target.");
		swerve.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		swerve.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(swerve);
	}
}
