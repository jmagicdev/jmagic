package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ajani Steadfast")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.AJANI})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Magic2015CoreSet.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE})
public final class AjaniSteadfast extends Card
{
	public static final class AjaniSteadfastAbility0 extends LoyaltyAbility
	{
		public AjaniSteadfastAbility0(GameState state)
		{
			super(state, +1, "Until end of turn, up to one target creature gets +1/+1 and gains first strike, vigilance, and lifelink.");

			Target t = this.addTarget(CreaturePermanents.instance(), "up to one target creature");
			t.setNumber(0, 1);

			EventFactory effect = ptChangeAndAbilityUntilEndOfTurn(targetedBy(t), +1, +1, "Until end of turn, up to one target creature gets +1/+1 and gains first strike, vigilance, and lifelink.", org.rnd.jmagic.abilities.keywords.FirstStrike.class, org.rnd.jmagic.abilities.keywords.Vigilance.class, org.rnd.jmagic.abilities.keywords.Lifelink.class);
			this.addEffect(effect);

		}
	}

	public static final class AjaniSteadfastAbility1 extends LoyaltyAbility
	{
		public AjaniSteadfastAbility1(GameState state)
		{
			super(state, -2, "Put a +1/+1 counter on each creature you control and a loyalty counter on each other planeswalker you control.");

			EventFactory ptCounters = putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, CREATURES_YOU_CONTROL, "Put a +1/+1 counter on each creature you control");

			SetGenerator yourPWs = Intersect.instance(HasType.instance(Type.PLANESWALKER), ControlledBy.instance(You.instance()));
			SetGenerator otherPWs = RelativeComplement.instance(yourPWs, ABILITY_SOURCE_OF_THIS);
			EventFactory loyaltyCounters = putCounters(1, Counter.CounterType.LOYALTY, otherPWs, "and a loyalty counter on each other planeswalker you control.");

			this.addEffect(simultaneous(ptCounters, loyaltyCounters));
		}
	}

	public static final class PreventEffect extends DamageReplacementEffect
	{
		public PreventEffect(Game game)
		{
			super(game, "If a source would deal damage to you or a planeswalker you control, prevent all but 1 of that damage.");
			this.makePreventionEffect();
		}

		@Override
		public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
		{
			DamageAssignment.Batch batch = new DamageAssignment.Batch();

			GameObject thisObject = (GameObject)this.getStaticSourceObject(context.game.actualState);
			Player you = thisObject.getController(thisObject.state);

			for(DamageAssignment damage: damageAssignments)
			{

				if(damage.takerID == you.ID)
					batch.add(damage);
				else
				{
					Identified taker = context.state.get(damage.takerID);
					if(!taker.isGameObject())
						continue;
					if(((Controllable)taker).getController(context.state).ID != you.ID)
						continue;
					if(((GameObject)taker).getTypes().contains(Type.PLANESWALKER))
						batch.add(damage);
				}
			}

			return batch;
		}

		@Override
		public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
		{
			java.util.Map<Integer, DamageAssignment.Batch> sortedBySource = new java.util.HashMap<>();
			for(DamageAssignment damage: damageAssignments)
			{
				if(!sortedBySource.containsKey(damage.sourceID))
					sortedBySource.put(damage.sourceID, new DamageAssignment.Batch());
				sortedBySource.get(damage.sourceID).add(damage);
			}

			for(java.util.Map.Entry<Integer, DamageAssignment.Batch> sourceEntry: sortedBySource.entrySet())
			{
				java.util.Map<Integer, DamageAssignment.Batch> sortedByTaker = new java.util.HashMap<>();
				for(DamageAssignment damage: sourceEntry.getValue())
				{
					if(!sortedByTaker.containsKey(damage.takerID))
						sortedByTaker.put(damage.takerID, new DamageAssignment.Batch());
					sortedByTaker.get(damage.takerID).add(damage);
				}

				for(java.util.Map.Entry<Integer, DamageAssignment.Batch> takerEntry: sortedByTaker.entrySet())
				{
					int size = takerEntry.getValue().size();
					int remove = size - 1;
					java.util.Iterator<DamageAssignment> iter = takerEntry.getValue().iterator();
					for(int i = 0; i < remove; i++)
						damageAssignments.remove(iter.next());
				}
			}

			return new java.util.LinkedList<EventFactory>();
		}
	}

	public static final class EmblemAbility extends StaticAbility
	{
		public EmblemAbility(GameState state)
		{
			super(state, "If a source would deal damage to you or a planeswalker you control, prevent all but 1 of that damage.");
			this.addEffectPart(replacementEffectPart(new PreventEffect(state.game)));
		}
	}

	public static final class AjaniSteadfastAbility2 extends LoyaltyAbility
	{
		public AjaniSteadfastAbility2(GameState state)
		{
			super(state, -7, "You get an emblem with \"If a source would deal damage to you or a planeswalker you control, prevent all but 1 of that damage.\"");

			EventFactory makeEmblem = new EventFactory(EventType.CREATE_EMBLEM, "You get an emblem with \"If a source would deal damage to you or a planeswalker you control, prevent all but 1 of that damage.\"");
			makeEmblem.parameters.put(EventType.Parameter.CAUSE, This.instance());
			makeEmblem.parameters.put(EventType.Parameter.ABILITY, Identity.instance(EmblemAbility.class));
			makeEmblem.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			this.addEffect(makeEmblem);
		}
	}

	public AjaniSteadfast(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(4);

		// +1: Until end of turn, up to one target creature gets +1/+1 and gains
		// first strike, vigilance, and lifelink.
		this.addAbility(new AjaniSteadfastAbility0(state));

		// -2: Put a +1/+1 counter on each creature you control and a loyalty
		// counter on each other planeswalker you control.
		this.addAbility(new AjaniSteadfastAbility1(state));

		// -7: You get an emblem with
		// "If a source would deal damage to you or a planeswalker you control, prevent all but 1 of that damage."
		this.addAbility(new AjaniSteadfastAbility2(state));
	}
}
