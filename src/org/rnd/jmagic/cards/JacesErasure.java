package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Jace's Erasure")
@Types({Type.ENCHANTMENT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class JacesErasure extends Card
{
	public static final class JacesErasureAbility0 extends EventTriggeredAbility
	{
		public JacesErasureAbility0(GameState state)
		{
			super(state, "Whenever you draw a card, you may have target player put the top card of his or her library into his or her graveyard.");
			SimpleEventPattern draw = new SimpleEventPattern(EventType.DRAW_ONE_CARD);
			draw.put(EventType.Parameter.PLAYER, You.instance());
			this.addPattern(draw);

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			EventFactory mill = millCards(target, 1, "Target player puts the top card of his or her library into his or her graveyard");
			this.addEffect(youMay(mill, "You may have target player put the top card of his or her library into his or her graveyard."));
		}
	}

	public JacesErasure(GameState state)
	{
		super(state);

		// Whenever you draw a card, you may have target player put the top card
		// of his or her library into his or her graveyard.
		this.addAbility(new JacesErasureAbility0(state));
	}
}
