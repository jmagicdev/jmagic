package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sengir Nosferatu")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("3BB")
@ColorIdentity({Color.BLACK})
public final class SengirNosferatu extends Card
{
	public static final class SengirNosferatuAbility1 extends ActivatedAbility
	{
		public static final class ChangeBack extends ActivatedAbility
		{
			public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("SengirNosferatu", "Choose an exiled card named Sengir Nosferatu", true);

			public ChangeBack(GameState state)
			{
				super(state, "(1)(B), Sacrifice this creature: Return an exiled card named Sengir Nosferatu to the battlefield under its owner's control.");
				this.setManaCost(new ManaPool("(1)(B)"));
				this.addCost(sacrificeThis("this creature"));

				SetGenerator exiled = InZone.instance(ExileZone.instance());
				SetGenerator namedSengirNosferatu = HasName.instance("Sengir Nosferatu");

				EventFactory choice = new EventFactory(EventType.PLAYER_CHOOSE, "Choose an exiled card named Sengir Nosferatu");
				choice.parameters.put(EventType.Parameter.PLAYER, You.instance());
				choice.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
				choice.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(exiled, Cards.instance(), namedSengirNosferatu));
				choice.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.OBJECTS, REASON));
				this.addEffect(choice);

				SetGenerator chosen = EffectResult.instance(choice);

				EventFactory putBack = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Return the chosen card to the battlefield under its owner's control.");
				putBack.parameters.put(EventType.Parameter.CAUSE, This.instance());
				putBack.parameters.put(EventType.Parameter.CONTROLLER, OwnerOf.instance(chosen));
				putBack.parameters.put(EventType.Parameter.OBJECT, chosen);
				this.addEffect(putBack);
			}
		}

		public SengirNosferatuAbility1(GameState state)
		{
			super(state, "(1)(B), Exile Sengir Nosferatu: Put a 1/2 black Bat creature token with flying onto the battlefield. It has \"(1)(B), Sacrifice this creature: Return an exiled card named Sengir Nosferatu to the battlefield under its owner's control.\"");
			this.setManaCost(new ManaPool("(1)(B)"));
			this.addCost(exileThis("Sengir Nosferatu"));

			CreateTokensFactory tokens = new CreateTokensFactory(1, 1, 2, "Put a 1/2 black Bat creature token with flying onto the battlefield. It has \"(1)(B), Sacrifice this creature: Return an exiled card named Sengir Nosferatu to the battlefield under its owner's control.\"");
			tokens.setColors(Color.BLACK);
			tokens.setSubTypes(SubType.BAT);
			tokens.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			tokens.addAbility(ChangeBack.class);
			this.addEffect(tokens.getEventFactory());
		}
	}

	public SengirNosferatu(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (1)(B), Exile Sengir Nosferatu: Put a 1/2 black Bat creature token
		// with flying onto the battlefield. It has
		// "(1)(B), Sacrifice this creature: Return an exiled card named Sengir Nosferatu to the battlefield under its owner's control."
		this.addAbility(new SengirNosferatuAbility1(state));
	}
}
