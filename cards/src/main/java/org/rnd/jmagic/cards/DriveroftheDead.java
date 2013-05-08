package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Driver of the Dead")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DriveroftheDead extends Card
{
	public static final class DriveroftheDeadAbility0 extends EventTriggeredAbility
	{
		public DriveroftheDeadAbility0(GameState state)
		{
			super(state, "When Driver of the Dead dies, return target creature card with converted mana cost 2 or less from your graveyard to the battlefield.");
			this.addPattern(whenThisDies());

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE), HasConvertedManaCost.instance(Between.instance(null, 2)), InZone.instance(GraveyardOf.instance(You.instance()))), "target creature card with converted mana cost 2 or less in your graveyard"));
			this.addEffect(putOntoBattlefield(target, "Return target creature card with converted mana cost 2 or less from your graveyard to the battlefield."));
		}
	}

	public DriveroftheDead(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// When Driver of the Dead dies, return target creature card with
		// converted mana cost 2 or less from your graveyard to the battlefield.
		this.addAbility(new DriveroftheDeadAbility0(state));
	}
}
