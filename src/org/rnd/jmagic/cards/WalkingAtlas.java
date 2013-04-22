package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Walking Atlas")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.COMMON)})
@ColorIdentity({})
public final class WalkingAtlas extends Card
{
	public static final class WalkingAtlasAbility0 extends ActivatedAbility
	{
		public WalkingAtlasAbility0(GameState state)
		{
			super(state, "(T): You may put a land card from your hand onto the battlefield.");
			this.costsTap = true;

			SetGenerator landCards = HasType.instance(Type.LAND);
			SetGenerator inYourHand = InZone.instance(HandOf.instance(You.instance()));
			SetGenerator landCardsInYourHand = Intersect.instance(landCards, inYourHand);

			EventFactory effect = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "Put a land card from your hand onto the battlefield.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			effect.parameters.put(EventType.Parameter.OBJECT, landCardsInYourHand);
			this.addEffect(youMay(effect, "You may put a land card from your hand onto the battlefield."));
		}
	}

	public WalkingAtlas(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): You may put a land card from your hand onto the battlefield.
		this.addAbility(new WalkingAtlasAbility0(state));
	}
}
