package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Phyrexian Ingester")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("6U")
@ColorIdentity({Color.BLUE})
public final class PhyrexianIngester extends Card
{
	public static final class PhyrexianIngesterAbility0 extends EventTriggeredAbility
	{
		public PhyrexianIngesterAbility0(GameState state)
		{
			super(state, "Imprint \u2014 When Phyrexian Ingester enters the battlefield, you may exile target nontoken creature.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), NonToken.instance()), "target nontoken creature"));
			EventFactory factory = exile(target, "Exile target nontoken creature.");
			factory.setLink(this);
			this.addEffect(youMay(factory, "You may exile target nontoken creature."));

			this.getLinkManager().addLinkClass(PhyrexianIngesterAbility1.class);
		}
	}

	public static final class PhyrexianIngesterAbility1 extends StaticAbility
	{
		public PhyrexianIngesterAbility1(GameState state)
		{
			super(state, "Phyrexian Ingester gets +X/+Y, where X is the exiled creature card's power and Y is its toughness.");

			SetGenerator exiled = ChosenFor.instance(LinkedTo.instance(Identity.instance(this)));

			this.addEffectPart(modifyPowerAndToughness(This.instance(), PowerOf.instance(exiled), ToughnessOf.instance(exiled)));

			this.getLinkManager().addLinkClass(PhyrexianIngesterAbility0.class);
		}
	}

	public PhyrexianIngester(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Imprint \u2014 When Phyrexian Ingester enters the battlefield, you
		// may exile target nontoken creature.
		this.addAbility(new PhyrexianIngesterAbility0(state));

		// Phyrexian Ingester gets +X/+Y, where X is the exiled creature card's
		// power and Y is its toughness.
		this.addAbility(new PhyrexianIngesterAbility1(state));
	}
}
