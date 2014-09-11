package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Cavern of Souls")
@Types({Type.LAND})
@ColorIdentity({})
public final class CavernofSouls extends Card
{
	public static final class ChooseType extends org.rnd.jmagic.abilities.AsThisEntersTheBattlefieldChooseACreatureType
	{
		public ChooseType(GameState state)
		{
			super(state, "As Cavern of Souls enters the battlefield, choose a creature type.");
			this.getLinkManager().addLinkClass(CavernofSoulsAbility2.class);
		}
	}

	private static final class CavernofSoulsMana extends ManaSymbol
	{
		private static final long serialVersionUID = 1L;

		private transient SetGenerator type;
		private int abilityID;

		private CavernofSoulsMana(SetGenerator type, int abilityID, int cavernOfSoulsID)
		{
			super("Cavern of Souls mana");
			this.colors = Color.allColors();
			this.type = type;
			this.abilityID = abilityID;
			this.sourceID = cavernOfSoulsID;
		}

		@Override
		public CavernofSoulsMana create()
		{
			CavernofSoulsMana s = new CavernofSoulsMana(this.type, this.abilityID, this.sourceID);
			s.colors = this.colors;
			s.colorless = this.colorless;
			return s;
		}

		public EventFactory getEffectForSpendingOn(GameObject o)
		{
			SetGenerator thatSpell = Identity.instance(o);

			SimpleEventPattern counterThatSpell = new SimpleEventPattern(EventType.COUNTER);
			counterThatSpell.put(EventType.Parameter.OBJECT, thatSpell);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(counterThatSpell));

			EventFactory shelter = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "That spell can't be countered.");
			shelter.parameters.put(EventType.Parameter.CAUSE, This.instance());
			shelter.parameters.put(EventType.Parameter.EFFECT, Identity.instance(part));
			return shelter;
		}

		@Override
		public boolean pays(GameState state, ManaSymbol cost)
		{
			if(!super.pays(state, cost))
				return false;

			if(cost.sourceID == -1)
				return false;

			GameObject source = state.getByIDObject(cost.sourceID);
			SubType type = this.type.evaluate(state, state.<GameObject>get(this.abilityID)).getOne(SubType.class);
			return source.isSpell() && source.getTypes().contains(Type.CREATURE) && source.getSubTypes().contains(type);
		}

		@Override
		public String toString()
		{
			return "(" + this.name + ")";
		}
	}

	public static final class CavernofSoulsAbility2 extends ActivatedAbility
	{
		public CavernofSoulsAbility2(GameState state)
		{
			super(state, "(T): Add one mana of any color to your mana pool. Spend this mana only to cast a creature spell of the chosen type, and that spell can't be countered.");
			this.costsTap = true;

			this.getLinkManager().addLinkClass(ChooseType.class);
			SetGenerator type = ChosenFor.instance(LinkedTo.instance(This.instance()));
			SetGenerator mana = Identity.instance(new CavernofSoulsMana(type, this.ID, this.sourceID));
			this.addEffect(addManaToYourManaPoolFromAbility(mana, "Add one mana of any color to your mana pool. Spend this mana only to cast a creature spell of the chosen type, and that spell can't be countered."));
		}
	}

	public CavernofSouls(GameState state)
	{
		super(state);

		// As Cavern of Souls enters the battlefield, choose a creature type.
		this.addAbility(new ChooseType(state));

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (T): Add one mana of any color to your mana pool. Spend this mana
		// only to cast a creature spell of the chosen type, and that spell
		// can't be countered.
		this.addAbility(new CavernofSoulsAbility2(state));
	}
}
