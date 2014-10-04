package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Battlefield Thaumaturge")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class BattlefieldThaumaturge extends Card
{
	public static final ContinuousEffectType BATTLEFIELD_THAUMATURGE_COST_REDUCTION = new ContinuousEffectType("BATTLEFIELD_THAUMATURGE_COST_REDUCTION")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public void apply(GameState state, ContinuousEffect effect, java.util.Map<Parameter, Set> parameters)
		{
			Type creature = Identity.instance(Type.CREATURE).evaluate(state, effect.getSourceObject()).getOne(Type.class);
			for(GameObject o: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			{
				Set reductionKey = new Set(o);

				int reductionAmount = 0;
				for(java.util.Map<Target, java.util.List<Target>> chosenTargets: o.getChosenTargets())
				{
					for(java.util.List<Target> chosenTarget: chosenTargets.values())
					{
						for(Target target: chosenTarget)
						{
							Identified targeted = state.get(target.targetID);
							if(targeted.isGameObject() && ((GameObject)targeted).getTypes().contains(creature))
								reductionAmount++;
						}
					}
				}

				ManaPool reductionPool = new ManaPool("(" + reductionAmount + ")");
				if(state.manaCostReductions.containsKey(reductionKey))
					state.manaCostReductions.get(reductionKey).addAll(reductionPool);
				else
					state.manaCostReductions.put(reductionKey, reductionPool);
			}
		}

		@Override
		public Layer layer()
		{
			return Layer.RULE_CHANGE;
		}

	};

	public static final class BattlefieldThaumaturgeAbility0 extends StaticAbility
	{
		public BattlefieldThaumaturgeAbility0(GameState state)
		{
			super(state, "Each instant and sorcery spell you cast costs (1) less to cast for each creature it targets.");

			SetGenerator yourSpells = Intersect.instance(ControlledBy.instance(You.instance(), Stack.instance()), HasType.instance(Type.INSTANT, Type.SORCERY));

			ContinuousEffect.Part part = new ContinuousEffect.Part(BATTLEFIELD_THAUMATURGE_COST_REDUCTION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, yourSpells);
			this.addEffectPart(part);
		}
	}

	public static final class BattlefieldThaumaturgeAbility1 extends EventTriggeredAbility
	{
		public BattlefieldThaumaturgeAbility1(GameState state)
		{
			super(state, "Heroic \u2014 Whenever you cast a spell that targets Battlefield Thaumaturge, Battlefield Thaumaturge gains hexproof until end of turn.");
			this.addPattern(heroic());
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Hexproof.class, "Battlefield Thaumaturge gains hexproof until end of turn."));
		}
	}

	public BattlefieldThaumaturge(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Each instant and sorcery spell you cast costs (1) less to cast for
		// each creature it targets.
		this.addAbility(new BattlefieldThaumaturgeAbility0(state));

		// Heroic \u2014 Whenever you cast a spell that targets Battlefield
		// Thaumaturge, Battlefield Thaumaturge gains hexproof until end of
		// turn.
		this.addAbility(new BattlefieldThaumaturgeAbility1(state));
	}
}
