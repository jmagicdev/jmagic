package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Eldrazi Temple")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class EldraziTemple extends Card
{
	public static final class EldraziTempleAbility1 extends ActivatedAbility
	{
		public EldraziTempleAbility1(GameState state)
		{
			super(state, "(T): Add (2) to your mana pool. Spend this mana only to cast colorless Eldrazi spells or activate abilities of colorless Eldrazi.");
			this.costsTap = true;

			MultipleSetPattern colorlessEldrazi = new MultipleSetPattern(true);
			colorlessEldrazi.addPattern(new SimpleSetPattern(Colorless.instance()));
			colorlessEldrazi.addPattern(new SubTypePattern(SubType.ELDRAZI));

			EventFactory effect = new EventFactory(ADD_RESTRICTED_MANA, "Add (2) to your mana pool. Spend this mana only to cast colorless Eldrazi spells or activate abilities of colorless Eldrazi.");
			effect.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			effect.parameters.put(EventType.Parameter.TYPE, Identity.instance(colorlessEldrazi));
			effect.parameters.put(EventType.Parameter.MANA, Identity.instance(ManaSymbol.ManaType.COLORLESS));
			effect.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			this.addEffect(effect);
		}
	}

	public EldraziTemple(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (T): Add (2) to your mana pool. Spend this mana only to cast
		// colorless Eldrazi spells or activate abilities of colorless Eldrazi.
		this.addAbility(new EldraziTempleAbility1(state));
	}
}
