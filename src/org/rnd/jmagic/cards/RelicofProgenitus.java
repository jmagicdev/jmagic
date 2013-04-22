package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Relic of Progenitus")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.COMMON)})
@ColorIdentity({})
public final class RelicofProgenitus extends Card
{
	public static final class ExileACard extends ActivatedAbility
	{
		public ExileACard(GameState state)
		{
			super(state, "(T): Target player exiles a card from his or her graveyard.");
			this.costsTap = true;
			Target target = this.addTarget(Players.instance(), "target player");

			EventFactory exile = new EventFactory(EventType.EXILE_CHOICE, "Target player exiles a card from his or her graveyard.");
			exile.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exile.parameters.put(EventType.Parameter.OBJECT, InZone.instance(GraveyardOf.instance(targetedBy(target))));
			exile.parameters.put(EventType.Parameter.PLAYER, targetedBy(target));
		}
	}

	public static final class ExileAllDraw extends ActivatedAbility
	{
		public ExileAllDraw(GameState state)
		{
			super(state, "(1), Exile Relic of Progenitus: Exile all cards from all graveyards. Draw a card.");
			this.setManaCost(new ManaPool("1"));

			this.addCost(exile(ABILITY_SOURCE_OF_THIS, "Exile Relic of Progenitus"));

			// TODO : This doesn't work. Convenience.exile passes a
			// ZoneContaining to MOVE_OBJECTS.
			this.addEffect(exile(InZone.instance(GraveyardOf.instance(Players.instance())), "Exile all cards from all graveyards."));
			this.addEffect(drawACard());
		}
	}

	public RelicofProgenitus(GameState state)
	{
		super(state);

		// (T): Target player exiles a card from his or her graveyard.
		this.addAbility(new ExileACard(state));

		// (1), Exile Relic of Progenitus: Exile all cards from all graveyards.
		// Draw a card.
		this.addAbility(new ExileAllDraw(state));
	}
}
