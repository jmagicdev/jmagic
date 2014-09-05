package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ruin Ghost")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class RuinGhost extends Card
{
	public static final class RuinGhostAbility0 extends ActivatedAbility
	{
		public RuinGhostAbility0(GameState state)
		{
			super(state, "(W), (T): Exile target land you control, then return it to the battlefield under your control.");
			this.setManaCost(new ManaPool("(W)"));
			this.costsTap = true;

			SetGenerator yourLands = Intersect.instance(LandPermanents.instance(), ControlledBy.instance(You.instance()));
			SetGenerator target = targetedBy(this.addTarget(yourLands, "target land you control"));

			EventFactory blink = new EventFactory(BLINK, "Exile target land you control, then return it to the battlefield under your control.");
			blink.parameters.put(EventType.Parameter.CAUSE, This.instance());
			blink.parameters.put(EventType.Parameter.PLAYER, You.instance());
			blink.parameters.put(EventType.Parameter.TARGET, target);
			this.addEffect(blink);
		}
	}

	public RuinGhost(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (W), (T): Exile target land you control, then return it to the
		// battlefield under your control.
		this.addAbility(new RuinGhostAbility0(state));
	}
}
