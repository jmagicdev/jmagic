package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Mul Daya Channelers")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.DRUID, SubType.ELF})
@ManaCost("1GG")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class MulDayaChannelers extends Card
{
	public static final class MulDayaChannelersAbility1 extends StaticAbility
	{
		public MulDayaChannelersAbility1(GameState state)
		{
			super(state, "As long as the top card of your library is a creature card, Mul Daya Channelers gets +3/+3.");

			SetGenerator topIsCreature = Intersect.instance(TopCards.instance(1, LibraryOf.instance(You.instance())), HasType.instance(Type.CREATURE));
			this.canApply = Both.instance(this.canApply, topIsCreature);

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +3, +3));
		}
	}

	public static final class TapForTwoColored extends ActivatedAbility
	{
		public TapForTwoColored(GameState state)
		{
			super(state, "(T): Add two mana of any one color to your mana pool.");
			this.costsTap = true;

			EventFactory mana = new EventFactory(EventType.ADD_MANA, "Add two mana of any one color to your mana pool.");
			mana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			mana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			mana.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			mana.parameters.put(EventType.Parameter.MANA, Identity.fromCollection(new ManaPool("(WUBRG)")));
			this.addEffect(mana);
		}
	}

	public static final class MulDayaChannelersAbility2 extends StaticAbility
	{
		public MulDayaChannelersAbility2(GameState state)
		{
			super(state, "As long as the top card of your library is a land card, Mul Daya Channelers has \"(T): Add two mana of any one color to your mana pool.\"");

			SetGenerator topIsLand = Intersect.instance(TopCards.instance(1, LibraryOf.instance(You.instance())), HasType.instance(Type.LAND));
			this.canApply = Both.instance(this.canApply, topIsLand);

			this.addEffectPart(addAbilityToObject(This.instance(), TapForTwoColored.class));
		}
	}

	public MulDayaChannelers(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Play with the top card of your library revealed.
		this.addAbility(new org.rnd.jmagic.abilities.RevealTopOfLibrary(state));

		// As long as the top card of your library is a creature card, Mul Daya
		// Channelers gets +3/+3.
		this.addAbility(new MulDayaChannelersAbility1(state));

		// As long as the top card of your library is a land card, Mul Daya
		// Channelers has
		// "(T): Add two mana of any one color to your mana pool."
		this.addAbility(new MulDayaChannelersAbility2(state));
	}
}
