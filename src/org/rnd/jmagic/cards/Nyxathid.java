package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nyxathid")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Expansion.CONFLUX, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class Nyxathid extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("Nyxathid", "Choose an opponent.", true);

	public static final class ChooseAnOpponent extends StaticAbility
	{
		/**
		 * @eparam CAUSE: the reason for choosing an opponent
		 * @eparam PLAYER: the player choosing an opponent
		 * @eparam CHOICE: the players the player can choose from
		 * @eparam SOURCE: the object to store the choice on
		 * @eparam RESULT: the chosen opponent
		 */
		public static final EventType DAMMIT_ANOTHER_CUSTOM_EVENTTYPE = new EventType("DAMMIT_ANOTHER_CUSTOM_EVENTTYPE")
		{
			@Override
			public Parameter affects()
			{
				return null;
			}

			@Override
			public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
			{
				Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
				java.util.List<Player> named = player.sanitizeAndChoose(game.actualState, 1, parameters.get(Parameter.CHOICE).getAll(Player.class), PlayerInterface.ChoiceType.PLAYER, REASON);
				Linkable link = parameters.get(Parameter.SOURCE).getOne(Linkable.class).getPhysical();
				for(Object object: named)
					link.getLinkManager().addLinkInformation(object);
				event.setResult(Identity.instance(named));
				return named.size() == 1;
			}
		};

		public ChooseAnOpponent(GameState state)
		{
			super(state, "As Nyxathid enters the battlefield, choose an opponent.");

			this.getLinkManager().addLinkClass(ProhibitAbilities.class);

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, "As Nyxathid enters the battlefield, choose an opponent");
			replacement.addPattern(asThisEntersTheBattlefield());

			SetGenerator originalEvent = replacement.replacedByThis();
			SetGenerator you = ControllerOf.instance(originalEvent);

			EventFactory factory = new EventFactory(DAMMIT_ANOTHER_CUSTOM_EVENTTYPE, "Choose an opponent.");
			factory.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(originalEvent));
			factory.parameters.put(EventType.Parameter.PLAYER, you);
			factory.parameters.put(EventType.Parameter.CHOICE, OpponentsOf.instance(you));
			factory.parameters.put(EventType.Parameter.SOURCE, Identity.instance(this));
			replacement.addEffect(factory);

			this.addEffectPart(replacementEffectPart(replacement));

			this.canApply = NonEmpty.instance();
		}
	}

	public static final class ProhibitAbilities extends StaticAbility
	{
		public ProhibitAbilities(GameState state)
		{
			super(state, "Nyxathid gets -1/-1 for each card in the chosen player's hand.");
			this.getLinkManager().addLinkClass(ChooseAnOpponent.class);

			SetGenerator cardsInHand = InZone.instance(HandOf.instance(ChosenFor.instance(LinkedTo.instance(Identity.instance(this)))));
			SetGenerator amount = Subtract.instance(numberGenerator(0), Count.instance(cardsInHand));
			this.addEffectPart(modifyPowerAndToughness(This.instance(), amount, amount));
		}
	}

	public Nyxathid(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		// As Nyxathid enters the battlefield, choose an opponent.
		this.addAbility(new ChooseAnOpponent(state));

		// Nyxathid gets -1/-1 for each card in the chosen player's hand.
		this.addAbility(new ProhibitAbilities(state));
	}
}
