package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Pardic Miner")
@Types({Type.CREATURE})
@SubTypes({SubType.DWARF})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Odyssey.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class PardicMiner extends Card
{
	public static final class PardicMinerAbility0 extends ActivatedAbility
	{
		public PardicMinerAbility0(GameState state)
		{
			super(state, "Sacrifice Pardic Miner: Target player can't play lands this turn.");
			this.addCost(sacrificeThis("Pardic Miner"));

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

			SimpleEventPattern castSpell = new SimpleEventPattern(EventType.PLAY_LAND);
			castSpell.put(EventType.Parameter.PLAYER, target);

			ContinuousEffect.Part prohibitEffect = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			prohibitEffect.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(castSpell));
			this.addEffect(createFloatingEffect("Target player can't play lands this turn.", prohibitEffect));
		}
	}

	public PardicMiner(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Sacrifice Pardic Miner: Target player can't play lands this turn.
		this.addAbility(new PardicMinerAbility0(state));
	}
}
