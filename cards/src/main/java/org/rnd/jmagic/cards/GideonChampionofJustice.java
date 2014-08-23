package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gideon, Champion of Justice")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.GIDEON})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE})
public final class GideonChampionofJustice extends Card
{
	public static final class GideonChampionofJusticeAbility0 extends LoyaltyAbility
	{
		public GideonChampionofJusticeAbility0(GameState state)
		{
			super(state, +1, "Put a loyalty counter on Gideon, Champion of Justice for each creature target opponent controls.");

			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));

			this.addEffect(putCounters(Count.instance(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(target))), Counter.CounterType.LOYALTY, ABILITY_SOURCE_OF_THIS, "Put a loyalty counter on Gideon, Champion of Justice for each creature target opponent controls."));
		}
	}

	public static final class GideonChampionofJusticeAbility1 extends LoyaltyAbility
	{
		private static final class PreventToGideon extends DamageReplacementEffect
		{
			private PreventToGideon(Game game, String name)
			{
				super(game, name);
				this.makePreventionEffect();
			}

			@Override
			public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
			{
				DamageAssignment.Batch ret = new DamageAssignment.Batch();

				Identified ability = this.getSourceObject(context.game.actualState);
				int gideon = ((ActivatedAbility)ability).sourceID;
				for(DamageAssignment assignment: damageAssignments)
					if(assignment.takerID == gideon)
						ret.add(assignment);

				return ret;
			}

			@Override
			public java.util.List<org.rnd.jmagic.engine.EventFactory> prevent(org.rnd.jmagic.engine.DamageAssignment.Batch damageAssignments)
			{
				damageAssignments.clear();
				return java.util.Collections.emptyList();
			}
		}

		public GideonChampionofJusticeAbility1(GameState state)
		{
			super(state, 0, "Until end of turn, Gideon, Champion of Justice becomes Human Soldier creature with power and toughness each equal to the number of loyalty counters on him and gains indestructible. He's still a planeswalker. Prevent all damage that would be dealt to him this turn.");

			SetGenerator num = Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.LOYALTY));
			ContinuousEffect.Part ptPart = setPowerAndToughness(ABILITY_SOURCE_OF_THIS, num, num);

			ContinuousEffect.Part typesPart = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			typesPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			typesPart.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.CREATURE, SubType.HUMAN, SubType.SOLDIER));

			ContinuousEffect.Part indestructiblePart = addAbilityToObject(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Indestructible.class);

			ContinuousEffect.Part preventPart = new ContinuousEffect.Part(ContinuousEffectType.REPLACEMENT_EFFECT);
			preventPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(new PreventToGideon(state.game, "Prevent all damage that would be dealt to Gideon Jura")));

			this.addEffect(createFloatingEffect("Until end of turn, Gideon Jura becomes a 6/6 Human Soldier creature that's still a planeswalker. Prevent all damage that would be dealt to him this turn.", ptPart, typesPart, indestructiblePart, preventPart));
		}
	}

	public static final class GideonChampionofJusticeAbility2 extends LoyaltyAbility
	{
		public GideonChampionofJusticeAbility2(GameState state)
		{
			super(state, -15, "Exile all other permanents.");

			this.addEffect(exile(RelativeComplement.instance(Permanents.instance(), ABILITY_SOURCE_OF_THIS), "Exile all other permanents."));
		}
	}

	public GideonChampionofJustice(GameState state)
	{
		super(state);

		this.setPrintedLoyalty((4));

		// +1: Put a loyalty counter on Gideon, Champion of Justice for each
		// creature target opponent controls.
		this.addAbility(new GideonChampionofJusticeAbility0(state));

		// 0: Until end of turn, Gideon, Champion of Justice becomes an
		// indestructible Human Soldier creature with power and toughness each
		// equal to the number of loyalty counters on him. He's still a
		// planeswalker. Prevent all damage that would be dealt to him this
		// turn.
		this.addAbility(new GideonChampionofJusticeAbility1(state));

		// -15: Exile all other permanents.
		this.addAbility(new GideonChampionofJusticeAbility2(state));
	}
}
