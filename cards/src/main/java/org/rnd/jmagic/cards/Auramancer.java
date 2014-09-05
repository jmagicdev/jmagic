package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Auramancer")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = Odyssey.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class Auramancer extends Card
{
	public static final class AuramancerAbility0 extends EventTriggeredAbility
	{
		public AuramancerAbility0(GameState state)
		{
			super(state, "When Auramancer enters the battlefield, you may return target enchantment card from your graveyard to your hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator yourYard = GraveyardOf.instance(You.instance());
			SetGenerator legal = Intersect.instance(HasType.instance(Type.ENCHANTMENT), InZone.instance(yourYard));
			SetGenerator target = targetedBy(this.addTarget(legal, "target enchantment card from your graveyard"));
			this.addEffect(youMay(putIntoHand(target, You.instance(), "Return target enchantment card from your graveyard to your hand.")));
		}
	}

	public Auramancer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Auramancer enters the battlefield, you may return target
		// enchantment card from your graveyard to your hand.
		this.addAbility(new AuramancerAbility0(state));
	}
}
