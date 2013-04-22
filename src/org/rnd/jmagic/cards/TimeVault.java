package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Time Vault")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.BETA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.RARE)})
@ColorIdentity({})
public final class TimeVault extends Card
{
	public static final class SkipToUntap extends StaticAbility
	{
		private static final class StartTurnWhileTapped implements EventPattern
		{
			@Override
			public boolean match(Event event, Identified identified, GameState state)
			{
				GameObject object = (GameObject)identified;

				if(event.type != EventType.BEGIN_TURN)
					return false;

				if(!object.isTapped())
					return false;

				Turn turn = event.parameters.get(EventType.Parameter.TURN).evaluate(state, event.getSource()).getOne(Turn.class);
				if(turn == null)
					throw new RuntimeException("shit!");

				if(turn.ownerID == object.controllerID)
					return true;

				return false;
			}

			@Override
			public boolean looksBackInTime()
			{
				return false;
			}

			@Override
			public boolean matchesManaAbilities()
			{
				return false;
			}
		}

		public SkipToUntap(GameState state)
		{
			super(state, "If you would begin your turn while Time Vault is tapped, you may skip that turn instead. If you do, untap Time Vault.");

			EventPattern toReplace = new StartTurnWhileTapped();
			EventReplacementEffect replacement = new EventReplacementEffect(state.game, "If you would begin your turn while Time Vault is tapped, you may skip that turn instead. If you do, untap Time Vault.", toReplace);

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.OBJECT, This.instance());
			replacement.addEffect(new EventFactory(EventType.UNTAP_PERMANENTS, parameters, "Untap Time Vault."));

			replacement.makeOptional(You.instance());

			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	public static final class ExtraTurn extends ActivatedAbility
	{
		public ExtraTurn(GameState state)
		{
			super(state, "(T): Take an extra turn after this one.");
			this.costsTap = true;

			this.addEffect(takeExtraTurns(You.instance(), 1, "Take an extra turn after this one."));
		}
	}

	public TimeVault(GameState state)
	{
		super(state);

		// Time Vault enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, "Time Vault"));

		// Time Vault doesn't untap during your untap step.
		this.addAbility(new org.rnd.jmagic.abilities.DoesntUntapDuringYourUntapStep(state, "Time Vault"));

		// If you would begin your turn while Time Vault is tapped, you may skip
		// that turn instead. If you do, untap Time Vault.
		this.addAbility(new SkipToUntap(state));

		// (T): Take an extra turn after this one.
		this.addAbility(new ExtraTurn(state));
	}
}
