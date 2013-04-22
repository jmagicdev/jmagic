package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Obstinate Baloth")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class ObstinateBaloth extends Card
{
	public static final class ObstinateBalothAbility0 extends EventTriggeredAbility
	{
		public ObstinateBalothAbility0(GameState state)
		{
			super(state, "When Obstinate Baloth enters the battlefield, you gain 4 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 4, "You gain 4 life."));
		}
	}

	public static final class ObstinateBalothAbility1 extends StaticAbility
	{
		public ObstinateBalothAbility1(GameState state)
		{
			super(state, "If a spell or ability an opponent controls causes you to discard Obstinate Baloth, put it onto the battlefield instead of putting it into your graveyard.");

			this.canApply = Intersect.instance(This.instance(), InZone.instance(HandOf.instance(You.instance())));

			SimpleEventPattern youDiscardMe = new SimpleEventPattern(EventType.DISCARD_ONE_CARD);
			youDiscardMe.put(EventType.Parameter.CAUSE, ControlledBy.instance(OpponentsOf.instance(You.instance()), Stack.instance()));
			youDiscardMe.put(EventType.Parameter.CARD, This.instance());
			EventReplacementEffect effect = new EventReplacementEffect(state.game, "If a spell or ability an opponent controls causes you to discard Obstinate Baloth, put it onto the battlefield instead of putting it into your graveyard.", youDiscardMe);

			EventFactory discardToPlay = new EventFactory(EventType.DISCARD_ONE_CARD, "Discard Obstinate Baloth, but put it onto the battlefield instead of putting it into your graveyard.");
			discardToPlay.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			discardToPlay.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			effect.addEffect(discardToPlay);

			this.addEffectPart(replacementEffectPart(effect));
		}
	}

	public ObstinateBaloth(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// When Obstinate Baloth enters the battlefield, you gain 4 life.
		this.addAbility(new ObstinateBalothAbility0(state));

		// If a spell or ability an opponent controls causes you to discard
		// Obstinate Baloth, put it onto the battlefield instead of putting it
		// into your graveyard.
		this.addAbility(new ObstinateBalothAbility1(state));
	}
}
