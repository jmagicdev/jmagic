package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Memory Erosion")
@Types({Type.ENCHANTMENT})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.RARE), @Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class MemoryErosion extends Card
{
	public static final class Erode extends EventTriggeredAbility
	{
		public Erode(GameState state)
		{
			super(state, "Whenever an opponent casts a spell, that player puts the top two cards of his or her library into his or her graveyard.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
			pattern.put(EventType.Parameter.OBJECT, Spells.instance());
			this.addPattern(pattern);

			SetGenerator thatPlayer = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.PLAYER);
			this.addEffect(millCards(thatPlayer, 2, "That player puts the top two cards of his or her library into his or her graveyard."));
		}
	}

	public MemoryErosion(GameState state)
	{
		super(state);

		this.addAbility(new Erode(state));
	}
}
