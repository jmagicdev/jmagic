package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Bala Ged Thief")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ROGUE, SubType.ALLY})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class BalaGedThief extends Card
{
	public static final class WorstRareAllyEver extends EventTriggeredAbility
	{
		public WorstRareAllyEver(GameState state)
		{
			super(state, "Whenever Bala Ged Thief or another Ally enters the battlefield under your control, target player reveals a number of cards from his or her hand equal to the number of Allies you control. You choose one of them. That player discards that card.");

			this.addPattern(allyTrigger());

			Target target = this.addTarget(Players.instance(), "target player");
			EventFactory effect = new EventFactory(REVEAL_SOME_CARDS_AND_DISCARD_FORCE, "Target player reveals a number of cards from his or her hand equal to the number of Allies you control. You choose one of them. That player discards that card.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.NUMBER, Count.instance(ALLIES_YOU_CONTROL));
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			effect.parameters.put(EventType.Parameter.TARGET, targetedBy(target));
			this.addEffect(effect);
		}
	}

	public BalaGedThief(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever Bala Ged Thief or another Ally enters the battlefield under
		// your control, target player reveals a number of cards from his or her
		// hand equal to the number of Allies you control. You choose one of
		// them. That player discards that card.
		this.addAbility(new WorstRareAllyEver(state));
	}
}
