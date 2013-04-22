package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.Convenience;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spellstutter Sprite")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.FAERIE})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class SpellstutterSprite extends Card
{
	public static final class Stutter extends EventTriggeredAbility
	{
		public Stutter(GameState state)
		{
			super(state, "When Spellstutter Sprite enters the battlefield, counter target spell with converted mana cost X or less, where X is the number of Faeries you control.");

			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator faeries = HasSubType.instance(SubType.FAERIE);
			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator faeriesYouControl = Intersect.instance(faeries, youControl);
			SetGenerator x = Between.instance(numberGenerator(0), Count.instance(faeriesYouControl));
			SetGenerator targetableSpells = Intersect.instance(Spells.instance(), HasConvertedManaCost.instance(x));
			Target target = this.addTarget(targetableSpells, "spell with converted mana cost X or less, where X is the number of Faeries you control");

			this.addEffect(Convenience.counter(targetedBy(target), "Counter target spell with converted mana cost X or less, where X is the number of Faeries you control."));
		}
	}

	public SpellstutterSprite(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new Stutter(state));
	}
}
