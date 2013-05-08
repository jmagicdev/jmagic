package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Azamuki, Treachery Incarnate")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@Flipped(CunningBandit.class)
@ColorIdentity({})
public final class AzamukiTreacheryIncarnate extends FlipBottomHalf
{
	public static final class CunningBanditAbility6 extends ActivatedAbility
	{
		public CunningBanditAbility6(GameState state)
		{
			super(state, "Remove a ki counter from Azamuki, Treachery Incarnate: Gain control of target creature until end of turn.");

			this.addCost(removeCountersFromThis(1, Counter.CounterType.KI, "Azamuki, Treachery Incarnate"));

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			this.addEffect(createFloatingEffect("Gain control of target creature until end of turn.", part));
		}
	}

	public AzamukiTreacheryIncarnate(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(2);

		// Remove a ki counter from Azamuki, Treachery Incarnate: Gain
		// control of target creature until end of turn.
		this.addAbility(new CunningBanditAbility6(state));
	}
}