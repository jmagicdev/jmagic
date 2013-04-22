package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Maul Splicer")
@Types({Type.CREATURE})
@SubTypes({SubType.ARTIFICER, SubType.HUMAN})
@ManaCost("6G")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class MaulSplicer extends Card
{
	public static final class MaulSplicerAbility0 extends EventTriggeredAbility
	{
		public MaulSplicerAbility0(GameState state)
		{
			super(state, "When Maul Splicer enters the battlefield, put two 3/3 colorless Golem artifact creature tokens onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory token = new CreateTokensFactory(2, 3, 3, "Put two 3/3 colorless Golem artifact creature tokens onto the battlefield.");
			token.setSubTypes(SubType.GOLEM);
			token.setArtifact();
			this.addEffect(token.getEventFactory());
		}
	}

	public static final class MaulSplicerAbility1 extends StaticAbility
	{
		public MaulSplicerAbility1(GameState state)
		{
			super(state, "Golem creatures you control have trample.");

			SetGenerator yourGolems = Intersect.instance(HasSubType.instance(SubType.GOLEM), CREATURES_YOU_CONTROL);
			this.addEffectPart(addAbilityToObject(yourGolems, org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public MaulSplicer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Maul Splicer enters the battlefield, put two 3/3 colorless Golem
		// artifact creature tokens onto the battlefield.
		this.addAbility(new MaulSplicerAbility0(state));

		// Golem creatures you control have trample.
		this.addAbility(new MaulSplicerAbility1(state));
	}
}
