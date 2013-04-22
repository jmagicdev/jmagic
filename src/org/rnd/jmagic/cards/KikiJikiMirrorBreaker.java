package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kiki-Jiki, Mirror Breaker")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.SHAMAN})
@ManaCost("2RRR")
@Printings({@Printings.Printed(ex = Expansion.FTV_LEGENDS, r = Rarity.MYTHIC), @Printings.Printed(ex = Expansion.CHAMPIONS_OF_KAMIGAWA, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class KikiJikiMirrorBreaker extends Card
{
	public static final class KikiJikiMirrorBreakerAbility1 extends ActivatedAbility
	{
		public KikiJikiMirrorBreakerAbility1(GameState state)
		{
			super(state, "(T): Put a token that's a copy of target nonlegendary creature you control onto the battlefield. That token has haste. Sacrifice it at the beginning of the next end step.");
			this.costsTap = true;

			SetGenerator yourNonlegends = RelativeComplement.instance(CREATURES_YOU_CONTROL, org.rnd.jmagic.engine.generators.HasSuperType.instance(org.rnd.jmagic.engine.SuperType.LEGENDARY));
			SetGenerator target = targetedBy(this.addTarget(yourNonlegends, "target nonlegendary creature you control"));

			EventFactory copyMe = new EventFactory(EventType.CREATE_TOKEN_COPY, "Put a token that's a copy of target nonlegendary creature you control onto the battlefield.");
			copyMe.parameters.put(EventType.Parameter.CAUSE, This.instance());
			copyMe.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			copyMe.parameters.put(EventType.Parameter.OBJECT, target);
			this.addEffect(copyMe);

			SetGenerator thatToken = NewObjectOf.instance(EffectResult.instance(copyMe));

			EventFactory haste = addAbilityUntilEndOfTurn(thatToken, org.rnd.jmagic.abilities.keywords.Haste.class, "That token has haste.");
			haste.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));
			this.addEffect(haste);

			EventFactory sacrifice = new EventFactory(EventType.SACRIFICE_PERMANENTS, "Sacrifice it");
			sacrifice.parameters.put(EventType.Parameter.CAUSE, This.instance());
			sacrifice.parameters.put(EventType.Parameter.PLAYER, You.instance());
			sacrifice.parameters.put(EventType.Parameter.PERMANENT, thatToken);

			EventFactory sacrificeLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Sacrifice it at the beginning of the next ends step.");
			sacrificeLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
			sacrificeLater.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfTheEndStep()));
			sacrificeLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(sacrifice));
			this.addEffect(sacrificeLater);
		}
	}

	public KikiJikiMirrorBreaker(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// (T): Put a token that's a copy of target nonlegendary creature you
		// control onto the battlefield. That token has haste. Sacrifice it at
		// the beginning of the next end step.
		this.addAbility(new KikiJikiMirrorBreakerAbility1(state));
	}
}
