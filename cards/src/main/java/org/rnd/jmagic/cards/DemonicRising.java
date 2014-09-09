package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Demonic Rising")
@Types({Type.ENCHANTMENT})
@ManaCost("3BB")
@ColorIdentity({Color.BLACK})
public final class DemonicRising extends Card
{
	public static final class DemonicRisingAbility0 extends EventTriggeredAbility
	{
		public DemonicRisingAbility0(GameState state)
		{
			super(state, "At the beginning of your end step, if you control exactly one creature, put a 5/5 black Demon creature token with flying onto the battlefield.");
			this.addPattern(atTheBeginningOfYourEndStep());
			this.interveningIf = Intersect.instance(numberGenerator(1), Count.instance(CREATURES_YOU_CONTROL));

			CreateTokensFactory factory = new CreateTokensFactory(1, 5, 5, "Put a 5/5 black Demon creature token with flying onto the battlefield.");
			factory.setColors(Color.BLACK);
			factory.setSubTypes(SubType.DEMON);
			factory.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(factory.getEventFactory());
		}
	}

	public DemonicRising(GameState state)
	{
		super(state);

		// At the beginning of your end step, if you control exactly one
		// creature, put a 5/5 black Demon creature token with flying onto the
		// battlefield.
		this.addAbility(new DemonicRisingAbility0(state));
	}
}
