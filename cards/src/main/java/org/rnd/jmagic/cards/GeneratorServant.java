package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Generator Servant")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class GeneratorServant extends Card
{
	public static final class GeneratorServantAbility0 extends ActivatedAbility
	{
		public GeneratorServantAbility0(GameState state)
		{
			super(state, "(T), Sacrifice Generator Servant: Add (2) to your mana pool. If that mana is spent on a creature spell, it gains haste until end of turn.");
			this.costsTap = true;
			this.addCost(sacrificeThis("Generator Servant"));

			EventFactory mana = new EventFactory(EventType.ADD_MANA, "Add (2) to your mana pool. If that mana is spent on a creature spell, it gains haste until end of turn.");
			mana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			mana.parameters.put(EventType.Parameter.MANA, Identity.instance(new GeneratorServantMana()));
			mana.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			mana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(mana);
		}
	}

	public static final class GeneratorServantMana extends ManaSymbol
	{
		private static final long serialVersionUID = 1L;

		public GeneratorServantMana()
		{
			super(ManaType.COLORLESS);
			this.name = "If this mana is spent on an creature spell, it gains haste until end of turn.";
		}

		@Override
		public EventFactory getEffectForSpendingOn(GameObject o)
		{
			if(!o.getTypes().contains(Type.CREATURE))
				return null;
			return addAbilityUntilEndOfTurn(Identity.instance(o), org.rnd.jmagic.abilities.keywords.Haste.class, "It gains haste until end of turn.");
		}

		@Override
		public ManaSymbol create()
		{
			GeneratorServantMana ret = new GeneratorServantMana();
			ret.sourceID = this.sourceID;
			return ret;
		}
	}

	public GeneratorServant(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// (T), Sacrifice Generator Servant: Add (2) to your mana pool. If that
		// mana is spent on a creature spell, it gains haste until end of turn.
		// (That creature can attack and (T) as soon as it comes under your
		// control.)
		this.addAbility(new GeneratorServantAbility0(state));
	}
}
