package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("\u00C6ther Adept")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class AEtherAdept extends Card
{
	public static final class AEtherAdeptAbility0 extends EventTriggeredAbility
	{
		public AEtherAdeptAbility0(GameState state)
		{
			super(state, "When \u00C6ther Adept enters the battlefield, return target creature to its owner's hand.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(bounce(target, "Return target creature to its owner's hand."));
		}
	}

	public AEtherAdept(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When \u00C6ther Adept enters the battlefield, return target creature
		// to its owner's hand.
		this.addAbility(new AEtherAdeptAbility0(state));
	}
}
