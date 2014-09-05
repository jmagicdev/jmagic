package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Wing Splicer")
@Types({Type.CREATURE})
@SubTypes({SubType.ARTIFICER, SubType.HUMAN})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class WingSplicer extends Card
{
	public static final class WingSplicerAbility0 extends EventTriggeredAbility
	{
		public WingSplicerAbility0(GameState state)
		{
			super(state, "When Wing Splicer enters the battlefield, put a 3/3 colorless Golem artifact creature token onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory token = new CreateTokensFactory(1, 3, 3, "Put a 3/3 colorless Golem artifact creature token onto the battlefield.");
			token.setSubTypes(SubType.GOLEM);
			token.setArtifact();
			this.addEffect(token.getEventFactory());
		}
	}

	public static final class WingSplicerAbility1 extends StaticAbility
	{
		public WingSplicerAbility1(GameState state)
		{
			super(state, "Golem creatures you control have flying.");

			SetGenerator yourGolems = Intersect.instance(HasSubType.instance(SubType.GOLEM), CREATURES_YOU_CONTROL);
			this.addEffectPart(addAbilityToObject(yourGolems, org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public WingSplicer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Wing Splicer enters the battlefield, put a 3/3 colorless Golem
		// artifact creature token onto the battlefield.
		this.addAbility(new WingSplicerAbility0(state));

		// Golem creatures you control have flying.
		this.addAbility(new WingSplicerAbility1(state));
	}
}
