package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Skyshroud Ranger")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class SkyshroudRanger extends Card
{
	public static final class PutLandOntoBattlefieldAbility extends ActivatedAbility
	{
		public PutLandOntoBattlefieldAbility(GameState state)
		{
			super(state, "(T): You may put a land card from your hand onto the battlefield. Activate this ability only any time you could play a sorcery.");
			this.costsTap = true;

			SetGenerator landCards = HasType.instance(Type.LAND);
			SetGenerator inYourHand = InZone.instance(HandOf.instance(You.instance()));
			SetGenerator landCardsInYourHand = Intersect.instance(landCards, inYourHand);

			EventFactory putOntoField = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "Put a land card from your hand onto the battlefield.");
			putOntoField.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putOntoField.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			putOntoField.parameters.put(EventType.Parameter.OBJECT, landCardsInYourHand);

			this.addEffect(youMay(putOntoField, "You may put a land card from your hand onto the battlefield."));

			this.activateOnlyAtSorcerySpeed();
		}
	}

	public SkyshroudRanger(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);
	}
}
