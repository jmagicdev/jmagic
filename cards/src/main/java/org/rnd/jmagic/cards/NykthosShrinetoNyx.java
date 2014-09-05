package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nykthos, Shrine to Nyx")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.THEROS, r = Rarity.RARE)})
@ColorIdentity({})
public final class NykthosShrinetoNyx extends Card
{
	public static final class NykthosShrinetoNyxAbility1 extends ActivatedAbility
	{
		public NykthosShrinetoNyxAbility1(GameState state)
		{
			super(state, "(2), (T): Choose a color. Add to your mana pool an amount of mana of that color equal to your devotion to that color.");
			this.setManaCost(new ManaPool("2"));
			this.costsTap = true;

			EventFactory chooseColor = playerChoose(You.instance(), 1, Identity.fromCollection(Color.allColors()), PlayerInterface.ChoiceType.COLOR, PlayerInterface.ChooseReason.CHOOSE_COLOR, "Choose a color.");
			this.addEffect(chooseColor);

			SetGenerator thatColor = EffectResult.instance(chooseColor);
			EventFactory mana = new EventFactory(EventType.ADD_MANA, "Add to your mana pool an amount of mana of that color equal to your devotion to that color.");
			mana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			mana.parameters.put(EventType.Parameter.MANA, thatColor);
			mana.parameters.put(EventType.Parameter.NUMBER, DevotionTo.instance(thatColor));
			mana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(mana);
		}
	}

	public NykthosShrinetoNyx(GameState state)
	{
		super(state);


		// {T}: Add {1} to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// {2}, {T}: Choose a color. Add to your mana pool an amount of mana of that color equal to your devotion to that color. (Your devotion to a color is the number of mana symbols of that color in the mana costs of permanents you control.)
		this.addAbility(new NykthosShrinetoNyxAbility1(state));
	}
}
