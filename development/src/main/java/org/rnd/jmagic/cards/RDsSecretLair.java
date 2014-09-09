package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("R&D's Secret Lair")
@Types({Type.LAND})
@ColorIdentity({})
public final class RDsSecretLair extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("SecretLair", "What kind of counter?", true);

	public static final class ShitIsCheap extends StaticAbility
	{
		public ShitIsCheap(GameState state)
		{
			super(state, "Players may pay (0) rather than pay the mana costs of spells they cast and nonmana abilities they activate of sources other than cards named R&D's Secret Lair");

			SetGenerator stuff = ControlledBy.instance(Players.instance(), Stack.instance());
			SetGenerator abilitiesOfThis = AbilitiesOf.instance(HasName.instance("R&D's Secret Lair"));

			ContinuousEffect.Part reduceThatFatMotherFucker = new ContinuousEffect.Part(ContinuousEffectType.ALTERNATE_COST);
			reduceThatFatMotherFucker.parameters.put(ContinuousEffectType.Parameter.OBJECT, RelativeComplement.instance(stuff, abilitiesOfThis));
			reduceThatFatMotherFucker.parameters.put(ContinuousEffectType.Parameter.PLAYER, Players.instance());
			reduceThatFatMotherFucker.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(new CostCollection(CostCollection.TYPE_ALTERNATE, "(0)")));
			this.addEffectPart(reduceThatFatMotherFucker);
		}
	}

	public static final class MakeTonsOfMana extends ActivatedAbility
	{
		public MakeTonsOfMana(GameState state)
		{
			super(state, "(0): Add twenty mana of any one color to your mana pool.");
			EventFactory effect = addManaToYourManaPoolFromAbility("(WUBRG)");
			effect.parameters.put(EventType.Parameter.NUMBER, numberGenerator(20));
			this.addEffect(effect);
		}
	}

	public static final class KillSomething extends ActivatedAbility
	{
		public KillSomething(GameState state)
		{
			super(state, "(0): Destroy target permanent.");
			Target target = this.addTarget(Permanents.instance(), "target permanent");
			this.addEffect(destroy(targetedBy(target), "Destroy target permanent."));
		}
	}

	public static final class MoveSomething extends ActivatedAbility
	{
		public MoveSomething(GameState state)
		{
			super(state, "(0): Reveal all cards. Move an object of your choice to a zone of your choice.");

			this.addEffect(reveal(Cards.instance(), "Reveal all objects."));

			SetGenerator yards = GraveyardOf.instance(Players.instance());
			SetGenerator hands = HandOf.instance(Players.instance());
			SetGenerator libraries = LibraryOf.instance(Players.instance());
			SetGenerator allZones = Union.instance(yards, hands, libraries, Battlefield.instance(), ExileZone.instance(), Stack.instance());
			EventFactory chooseSource = playerChoose(You.instance(), 1, allZones, PlayerInterface.ChoiceType.STRING, new PlayerInterface.ChooseReason("RDsSecretLair", "Choose a source zone.", true), "");
			this.addEffect(chooseSource);

			EventFactory choose = playerChoose(You.instance(), 1, InZone.instance(EffectResult.instance(chooseSource)), PlayerInterface.ChoiceType.OBJECTS, new PlayerInterface.ChooseReason("RDsSecretLair", "Choose an object.", true), "");
			this.addEffect(choose);

			EventFactory chooseZone = playerChoose(You.instance(), 1, allZones, PlayerInterface.ChoiceType.STRING, new PlayerInterface.ChooseReason("RDsSecretLair", "Choose a destination zone.", true), "");
			this.addEffect(chooseZone);

			EventFactory move = new EventFactory(EventType.MOVE_OBJECTS, "Move an object of your choice to a zone of your choice.");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.OBJECT, EffectResult.instance(choose));
			// this gives a controller parameter even though it's prohibited
			// when the zone isn't the stack or the battlefield
			// DON'T DO THIS
			move.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			move.parameters.put(EventType.Parameter.TO, EffectResult.instance(chooseZone));
			this.addEffect(move);
		}
	}

	public static final class MakeHaste extends ActivatedAbility
	{
		public MakeHaste(GameState state)
		{
			super(state, "(0): Target creature gains haste until end of turn.");
			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(addAbilityUntilEndOfTurn(targetedBy(target), org.rnd.jmagic.abilities.keywords.Haste.class, "Target creature gains haste until end of turn."));
		}
	}

	public static final class DiscardSomething extends ActivatedAbility
	{
		public DiscardSomething(GameState state)
		{
			super(state, "(0): Discard a card.");
			this.addEffect(discardCards(You.instance(), 1, "Discard a card."));
		}
	}

	public static final class DrawSomething extends ActivatedAbility
	{
		public DrawSomething(GameState state)
		{
			super(state, "(0): Draw a card.");
			this.addEffect(drawACard());
		}
	}

	public static final class PumpPlaneswalker extends ActivatedAbility
	{
		public PumpPlaneswalker(GameState state)
		{
			super(state, "(0): Put any number of any one kind of counter on target permanent.");
			Target target = this.addTarget(Permanents.instance(), "target permanent");

			EventFactory number = new EventFactory(EventType.PLAYER_CHOOSE_NUMBER, "");
			number.parameters.put(EventType.Parameter.PLAYER, You.instance());
			number.parameters.put(EventType.Parameter.CHOICE, Between.instance(0, null));
			number.parameters.put(EventType.Parameter.TYPE, Identity.instance("How many counters?"));
			this.addEffect(number);

			EventFactory type = new EventFactory(EventType.PLAYER_CHOOSE, "");
			type.parameters.put(EventType.Parameter.PLAYER, You.instance());
			type.parameters.put(EventType.Parameter.CHOICE, Identity.instance((Object[])Counter.CounterType.values()));
			type.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.ENUM, REASON));
			this.addEffect(type);

			EventFactory factory = new EventFactory(EventType.PUT_COUNTERS, "Put any number of any one kind of counter on target permanent.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, EffectResult.instance(number));
			factory.parameters.put(EventType.Parameter.COUNTER, EffectResult.instance(type));
			factory.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			this.addEffect(factory);
		}
	}

	public static final class SetLifeTotal extends ActivatedAbility
	{
		public SetLifeTotal(GameState state)
		{
			super(state, "(0): Set the life total of target player to any number.");

			Target targetPlayer = this.addTarget(Players.instance(), "target player");

			EventFactory number = new EventFactory(EventType.PLAYER_CHOOSE_NUMBER, "");
			number.parameters.put(EventType.Parameter.PLAYER, You.instance());
			number.parameters.put(EventType.Parameter.CHOICE, Between.instance(0, null));
			number.parameters.put(EventType.Parameter.TYPE, Identity.instance("What life total?"));
			this.addEffect(number);

			EventFactory factory = new EventFactory(EventType.SET_LIFE, "Set the life total of target player to any number.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, EffectResult.instance(number));
			factory.parameters.put(EventType.Parameter.PLAYER, targetedBy(targetPlayer));
			this.addEffect(factory);
		}
	}

	public static final class Twiddle extends ActivatedAbility
	{
		public Twiddle(GameState state)
		{
			super(state, "(0): Tap or untap target permanent.");
			Target t = this.addTarget(Permanents.instance(), "target permanent");
			this.addEffect(tapOrUntap(targetedBy(t), "target permanent"));
		}
	}

	public static final class TimeStop extends ActivatedAbility
	{
		public TimeStop(GameState state)
		{
			super(state, "(0): End the turn.");
			EventType.ParameterMap endTheTurnParameters = new EventType.ParameterMap();
			endTheTurnParameters.put(EventType.Parameter.CAUSE, This.instance());
			this.addEffect(new EventFactory(EventType.END_THE_TURN, endTheTurnParameters, "End the turn."));
		}
	}

	public RDsSecretLair(GameState state)
	{
		super(state);

		this.addAbility(new ShitIsCheap(state));
		this.addAbility(new MakeTonsOfMana(state));
		this.addAbility(new org.rnd.jmagic.abilities.PlayExtraLands.Final(state, 7, "You may play seven additional lands on each of your turns."));
		this.addAbility(new KillSomething(state));
		this.addAbility(new MoveSomething(state));
		this.addAbility(new MakeHaste(state));
		this.addAbility(new DiscardSomething(state));
		this.addAbility(new DrawSomething(state));
		this.addAbility(new PumpPlaneswalker(state));
		this.addAbility(new Twiddle(state));
		this.addAbility(new TimeStop(state));
		this.addAbility(new SetLifeTotal(state));
	}
}
