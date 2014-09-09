package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Vedalken Engineer")
@Types({Type.CREATURE})
@SubTypes({SubType.ARTIFICER, SubType.VEDALKEN})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class VedalkenEngineer extends Card
{
	public static final class VedalkenEngineerMana extends ActivatedAbility
	{
		public VedalkenEngineerMana(GameState state)
		{
			super(state, "(T): Add two mana of any one color to your mana pool. Spend this mana only to cast artifact spells or activate abilities of artifacts.");

			this.costsTap = true;

			EventFactory effect = new EventFactory(ADD_RESTRICTED_MANA, "Add two mana of any one color to your mana pool. Spend this mana only to cast artifact spells or activate abilities of artifacts.");
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			effect.parameters.put(EventType.Parameter.MANA, Identity.fromCollection(Color.allColors()));
			effect.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			effect.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			effect.parameters.put(EventType.Parameter.TYPE, Identity.instance(new TypePattern(Type.ARTIFACT)));
			this.addEffect(effect);
		}
	}

	public VedalkenEngineer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Add two mana of any one color to your mana pool. Spend this mana
		// only to cast artifact spells or activate abilities of artifacts.
		this.addAbility(new VedalkenEngineerMana(state));
	}
}
