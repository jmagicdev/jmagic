package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fiend of the Shadows")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.VAMPIRE})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class FiendoftheShadows extends Card
{
	public static final class FiendoftheShadowsAbility1 extends EventTriggeredAbility
	{
		public FiendoftheShadowsAbility1(GameState state)
		{
			super(state, "Whenever Fiend of the Shadows deals combat damage to a player, that player exiles a card from his or her hand. You may play that card for as long as it remains exiled.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			SetGenerator thatPlayer = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));

			EventFactory exileFactory = exile(thatPlayer, InZone.instance(HandOf.instance(You.instance())), 1, "That player exiles a card from his or her hand.");
			this.addEffect(exileFactory);

			SetGenerator thatCard = NewObjectOf.instance(EffectResult.instance(exileFactory));

			ContinuousEffect.Part playEffect = new ContinuousEffect.Part(ContinuousEffectType.MAY_PLAY_LOCATION);
			playEffect.parameters.put(ContinuousEffectType.Parameter.OBJECT, thatCard);
			playEffect.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(new PlayPermission(You.instance())));

			EventFactory mayPlay = createFloatingEffect(thatCard, "You may play that card for as long as it remains exiled.", playEffect);
			mayPlay.parameters.put(EventType.Parameter.EXPIRES, Not.instance(Exists.instance(thatCard)));
			this.addEffect(mayPlay);
		}
	}

	public static final class FiendoftheShadowsAbility2 extends ActivatedAbility
	{
		public FiendoftheShadowsAbility2(GameState state)
		{
			super(state, "Sacrifice a Human: Regenerate Fiend of the Shadows.");
			// Sacrifice a Human
			this.addCost(sacrifice(You.instance(), 1, HasSubType.instance(SubType.HUMAN), "Sacrifice a Human"));
			this.addEffect(regenerate(ABILITY_SOURCE_OF_THIS, "Regenerate Fiend of the Shadows."));
		}
	}

	public FiendoftheShadows(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Fiend of the Shadows deals combat damage to a player, that
		// player exiles a card from his or her hand. You may play that card for
		// as long as it remains exiled.
		this.addAbility(new FiendoftheShadowsAbility1(state));

		// Sacrifice a Human: Regenerate Fiend of the Shadows.
		this.addAbility(new FiendoftheShadowsAbility2(state));
	}
}
