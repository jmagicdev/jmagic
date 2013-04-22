package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Tunnel Ignus")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class TunnelIgnus extends Card
{
	public static final class TunnelIgnusAbility0 extends EventTriggeredAbility
	{
		public TunnelIgnusAbility0(GameState state)
		{
			super(state, "Whenever a land enters the battlefield under an opponent's control, if that player had another land enter the battlefield under his or her control this turn, Tunnel Ignus deals 3 damage to that player.");
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), HasType.instance(Type.LAND), OpponentsOf.instance(You.instance()), false));
			state.ensureTracker(new LandsPutOntoTheBattlefieldThisTurnCounter());
			SetGenerator thatPlayer = ControllerOf.instance(TriggerZoneChange.instance(This.instance()));
			this.interveningIf = Intersect.instance(Between.instance(1, null), MaximumPerPlayer.instance(LandsPutOntoTheBattlefieldThisTurnCounter.class, thatPlayer));
			this.addEffect(permanentDealDamage(3, thatPlayer, "Tunnel Ignus deals 3 damage to that player."));
		}
	}

	public TunnelIgnus(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Whenever a land enters the battlefield under an opponent's control,
		// if that player had another land enter the battlefield under his or
		// her control this turn, Tunnel Ignus deals 3 damage to that player.
		this.addAbility(new TunnelIgnusAbility0(state));
	}
}
