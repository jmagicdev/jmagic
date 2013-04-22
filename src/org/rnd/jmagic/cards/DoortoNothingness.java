package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Door to Nothingness")
@Types({Type.ARTIFACT})
@ManaCost("5")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FIFTH_DAWN, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.GREEN, Color.BLACK, Color.RED})
public final class DoortoNothingness extends Card
{
	public static final Game.LoseReason DOOR_LOSE_REASON = new Game.LoseReason("DoorToNothingness");

	public static final class DoortoNothingnessAbility1 extends ActivatedAbility
	{
		public DoortoNothingnessAbility1(GameState state)
		{
			super(state, "(W)(W)(U)(U)(B)(B)(R)(R)(G)(G), (T), Sacrifice Door to Nothingness: Target player loses the game.");
			this.setManaCost(new ManaPool("(W)(W)(U)(U)(B)(B)(R)(R)(G)(G)"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Door to Nothingness"));

			Target target = this.addTarget(Players.instance(), "Target player");
			EventType.ParameterMap loseGameParameters = new EventType.ParameterMap();
			loseGameParameters.put(EventType.Parameter.CAUSE, Union.instance(ABILITY_SOURCE_OF_THIS, Identity.instance(DOOR_LOSE_REASON)));
			loseGameParameters.put(EventType.Parameter.PLAYER, targetedBy(target));
			this.addEffect(new EventFactory(EventType.LOSE_GAME, loseGameParameters, "loses the game"));
		}
	}

	public DoortoNothingness(GameState state)
	{
		super(state);

		// Door to Nothingness enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (W)(W)(U)(U)(B)(B)(R)(R)(G)(G), (T), Sacrifice Door to Nothingness:
		// Target player loses the game.
		this.addAbility(new DoortoNothingnessAbility1(state));
	}
}
