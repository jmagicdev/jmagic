package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bruna, Light of Alabaster")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("3WWU")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class BrunaLightofAlabaster extends Card
{
	public static final PlayerInterface.ChooseReason BRUNA_FIELD = new PlayerInterface.ChooseReason("BrunaLightofAlabaster", "Choose Auras on the battlefield.", true);
	public static final PlayerInterface.ChooseReason BRUNA_OTHER = new PlayerInterface.ChooseReason("BrunaLightofAlabaster", "Choose Aura cards from your graveyard and hand.", true);

	public static final class BrunaLightofAlabasterAbility1 extends EventTriggeredAbility
	{
		public BrunaLightofAlabasterAbility1(GameState state)
		{
			super(state, "Whenever Bruna, Light of Alabaster attacks or blocks, you may attach to it any number of Auras on the battlefield and you may put onto the battlefield attached to it any number of Aura cards that could enchant it from your graveyard and/or hand.");
			this.addPattern(whenThisAttacks());
			this.addPattern(whenThisBlocks());

			SetGenerator onField = Intersect.instance(Permanents.instance(), CouldEnchant.instance(ABILITY_SOURCE_OF_THIS));
			SetGenerator allowedOnField = Between.instance(numberGenerator(0), Count.instance(onField));
			EventFactory chooseOnField = playerChoose(You.instance(), allowedOnField, onField, PlayerInterface.ChoiceType.OBJECTS, BRUNA_FIELD, "");
			this.addEffect(chooseOnField);

			EventFactory attach = new EventFactory(EventType.ATTACH, "You may attach to it any number of Auras on the battlefield");
			attach.parameters.put(EventType.Parameter.OBJECT, EffectResult.instance(chooseOnField));
			attach.parameters.put(EventType.Parameter.TARGET, ABILITY_SOURCE_OF_THIS);
			this.addEffect(attach);

			SetGenerator yardOrHand = Union.instance(GraveyardOf.instance(You.instance()), HandOf.instance(You.instance()));
			SetGenerator notOnField = Intersect.instance(InZone.instance(yardOrHand), CouldEnchant.instance(ABILITY_SOURCE_OF_THIS));
			SetGenerator allowedNotOnField = Between.instance(numberGenerator(0), Count.instance(notOnField));
			EventFactory chooseNotOnField = playerChoose(You.instance(), allowedNotOnField, notOnField, PlayerInterface.ChoiceType.OBJECTS, BRUNA_OTHER, "");
			this.addEffect(chooseNotOnField);

			EventFactory springForth = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_ATTACHED_TO, "and you may put onto the battlefield attached to it any number of Aura cards that could enchant it from your graveyard and/or hand.");
			springForth.parameters.put(EventType.Parameter.CAUSE, This.instance());
			springForth.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			springForth.parameters.put(EventType.Parameter.OBJECT, EffectResult.instance(chooseNotOnField));
			springForth.parameters.put(EventType.Parameter.TARGET, ABILITY_SOURCE_OF_THIS);
			this.addEffect(springForth);
		}
	}

	public BrunaLightofAlabaster(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying, vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// Whenever Bruna, Light of Alabaster attacks or blocks, you may attach
		// to it any number of Auras on the battlefield and you may put onto the
		// battlefield attached to it any number of Aura cards that could
		// enchant it from your graveyard and/or hand.
		this.addAbility(new BrunaLightofAlabasterAbility1(state));
	}
}
