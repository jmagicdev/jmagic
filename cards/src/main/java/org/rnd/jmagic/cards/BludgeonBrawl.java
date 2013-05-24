package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bludgeon Brawl")
@Types({Type.ENCHANTMENT})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class BludgeonBrawl extends Card
{
	public static final class GrantedAbilityWithNumberKey extends Game.GrantedAbilityKey
	{
		private int number;

		public GrantedAbilityWithNumberKey(Identified grantedBy, Class<?> ability, int destID, int number)
		{
			super(grantedBy, ability, destID);
			this.number = number;
		}

		@Override
		public boolean equals(Object obj)
		{
			if(this == obj)
				return true;
			if(!super.equals(obj))
				return false;
			if(getClass() != obj.getClass())
				return false;
			GrantedAbilityWithNumberKey other = (GrantedAbilityWithNumberKey)obj;
			if(this.number != other.number)
				return false;
			return true;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + this.number;
			return result;
		}
	}

	public static final class PumpX extends StaticAbility
	{
		private final int X;

		public PumpX(GameState state, int X)
		{
			super(state, "Equipped creature gets +" + X + "/+0.");
			this.X = X;

			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), X, 0));
		}

		@Override
		public PumpX create(Game game)
		{
			return new PumpX(game.physicalState, this.X);
		}
	}

	/**
	 * @eparam OBJECT: the objects to grant the abilities to
	 */
	public static final ContinuousEffectType BLUDGEON_BRAWL_EFFECT = new ContinuousEffectType("BLUDGEON_BRAWL_EFFECT")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public void apply(GameState state, ContinuousEffect effect, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Set<GameObject> objects = parameters.get(Parameter.OBJECT).getAll(GameObject.class);

			for(GameObject object: objects)
			{
				int cmc = object.getConvertedManaCost();

				{
					Game.GrantedAbilityKey key = new GrantedAbilityWithNumberKey(effect.getSourceObject(), org.rnd.jmagic.abilities.keywords.Equip.class, object.ID, cmc);
					Keyword physicalAbility = null;
					if(state.game.grantedAbilities.containsKey(key))
						physicalAbility = state.game.physicalState.get(state.game.grantedAbilities.get(key));
					else
					{
						physicalAbility = new org.rnd.jmagic.abilities.keywords.Equip(state.game.physicalState, "(" + cmc + ")");
						state.game.grantedAbilities.put(key, physicalAbility.ID);
					}

					Keyword instance;
					if(state.containsIdentified(physicalAbility.ID))
						instance = state.copyForEditing(physicalAbility);
					else
						instance = physicalAbility.clone(state);

					instance.createdByTimestamp = effect.getTimestamp();
					instance.grantedByID = effect.ID;
					object.addAbility(instance);
				}

				{
					Game.GrantedAbilityKey key = new GrantedAbilityWithNumberKey(effect.getSourceObject(), PumpX.class, object.ID, cmc);
					StaticAbility physicalAbility = null;
					if(state.game.grantedAbilities.containsKey(key))
						physicalAbility = state.game.physicalState.get(state.game.grantedAbilities.get(key));
					else
						physicalAbility = new PumpX(state.game.physicalState, cmc);

					StaticAbility instance;
					if(state.containsIdentified(physicalAbility.ID))
						instance = state.get(physicalAbility.ID);
					else
						instance = physicalAbility.clone(state);

					// Make sure the static ability's continuous effect is
					// in this state
					if(!state.containsIdentified(instance.effectID))
						physicalAbility.getEffect().clone(state);

					instance.createdByTimestamp = effect.getTimestamp();
					instance.grantedByID = effect.ID;
					object.addAbility(instance);
				}
			}
		}

		@Override
		public Layer layer()
		{
			return Layer.ABILITY_ADD_OR_REMOVE;
		}

	};

	public static final class BludgeonBrawlAbility0 extends StaticAbility
	{
		public BludgeonBrawlAbility0(GameState state)
		{
			super(state, "Each noncreature, non-Equipment artifact is an Equipment with equip (X) and \"Equipped creature gets +X/+0,\" where X is that artifact's converted mana cost.");

			SetGenerator objects = RelativeComplement.instance(ArtifactPermanents.instance(), Union.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.EQUIPMENT)));

			ContinuousEffect.Part equipment = new ContinuousEffect.Part(ContinuousEffectType.SET_TYPES);
			equipment.parameters.put(ContinuousEffectType.Parameter.OBJECT, objects);
			equipment.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.EQUIPMENT));
			this.addEffectPart(equipment);

			ContinuousEffect.Part part = new ContinuousEffect.Part(BLUDGEON_BRAWL_EFFECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, objects);
			this.addEffectPart(part);
		}
	}

	public BludgeonBrawl(GameState state)
	{
		super(state);

		// Each noncreature, non-Equipment artifact is an Equipment with equip
		// (X) and "Equipped creature gets +X/+0," where X is that artifact's
		// converted mana cost.
		this.addAbility(new BludgeonBrawlAbility0(state));
	}
}
