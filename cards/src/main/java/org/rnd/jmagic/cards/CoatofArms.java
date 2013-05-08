package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Coat of Arms")
@Types({Type.ARTIFACT})
@ManaCost("5")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.RARE), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.EXODUS, r = Rarity.RARE)})
@ColorIdentity({})
public final class CoatofArms extends Card
{
	public static final class Mafia extends StaticAbility
	{
		/**
		 * @eparam OBJECT: all creatures in play
		 */
		public static final ContinuousEffectType COAT_OF_ARMS_EFFECT = new ContinuousEffectType("COAT_OF_ARMS_EFFECT")
		{
			@Override
			public Parameter affects()
			{
				return ContinuousEffectType.Parameter.OBJECT;
			}

			@Override
			public void apply(GameState state, ContinuousEffect effect, java.util.Map<Parameter, Set> parameters)
			{
				java.util.Map<GameObject, Integer> sharedTypes = new java.util.HashMap<GameObject, Integer>();

				// Note: this algorithm ignores creatures with no creature types
				// since they do not contribute anything.
				for(GameObject creature: parameters.get(ContinuousEffectType.Parameter.OBJECT).getAll(GameObject.class))
					if(creature.getTypes().contains(Type.CREATURE) && !creature.getSubTypes().isEmpty())
					{
						int count = 0;
						for(java.util.Map.Entry<GameObject, Integer> previousMapping: sharedTypes.entrySet())
						{
							GameObject previousCreature = previousMapping.getKey();
							for(SubType type: SubType.getSubTypesFor(Type.CREATURE, previousCreature.getSubTypes()))
								if(creature.getSubTypes().contains(type))
								{
									previousMapping.setValue(previousMapping.getValue() + 1);
									count++;
									break;
								}
						}

						sharedTypes.put(creature, count);
					}

				for(java.util.Map.Entry<GameObject, Integer> entry: sharedTypes.entrySet())
				{
					GameObject creature = entry.getKey();
					int pump = entry.getValue();

					creature.setPower(creature.getPower() + pump);
					creature.setToughness(creature.getToughness() + pump);
				}
			}

			@Override
			public Layer layer()
			{
				return Layer.POWER_AND_TOUGHNESS;
			}

			@Override
			public SubLayer subLayer()
			{
				return SubLayer.MODIFY;
			}

			@Override
			public String toString()
			{
				return "COAT_OF_ARMS_EFFECT";
			}
		};

		public Mafia(GameState state)
		{
			super(state, "Each creature gets +1/+1 for each other creature on the battlefield that shares at least one creature type with it.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(COAT_OF_ARMS_EFFECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, CreaturePermanents.instance());
			this.addEffectPart(part);
		}
	}

	public CoatofArms(GameState state)
	{
		super(state);

		this.addAbility(new Mafia(state));
	}
}
