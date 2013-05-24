package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Loxodon Smiter")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEPHANT, SubType.SOLDIER})
@ManaCost("1GW")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class LoxodonSmiter extends Card
{
	public static final class LoxodonSmiterAbility1 extends StaticAbility
	{
		public LoxodonSmiterAbility1(GameState state)
		{
			super(state, "If a spell or ability an opponent controls causes you to discard Loxodon Smiter, put it onto the battlefield instead of putting it into your graveyard.");

			this.canApply = Intersect.instance(This.instance(), InZone.instance(HandOf.instance(You.instance())));

			SimpleEventPattern youDiscardMe = new SimpleEventPattern(EventType.DISCARD_ONE_CARD);
			youDiscardMe.put(EventType.Parameter.CAUSE, ControlledBy.instance(OpponentsOf.instance(You.instance()), Stack.instance()));
			youDiscardMe.put(EventType.Parameter.CARD, This.instance());
			EventReplacementEffect effect = new EventReplacementEffect(state.game, "If a spell or ability an opponent controls causes you to discard Loxodon Smiter, put it onto the battlefield instead of putting it into your graveyard.", youDiscardMe);

			EventFactory discardToPlay = new EventFactory(EventType.DISCARD_ONE_CARD, "Discard Loxodon Smiter, but put it onto the battlefield instead of putting it into your graveyard.");
			discardToPlay.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			discardToPlay.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			effect.addEffect(discardToPlay);

			this.addEffectPart(replacementEffectPart(effect));
		}
	}

	public LoxodonSmiter(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Loxodon Smiter can't be countered.
		this.addAbility(new org.rnd.jmagic.abilities.CantBeCountered(state, "Loxodon Smiter"));

		// If a spell or ability an opponent controls causes you to discard
		// Loxodon Smiter, put it onto the battlefield instead of putting it
		// into your graveyard.
		this.addAbility(new LoxodonSmiterAbility1(state));
	}
}
