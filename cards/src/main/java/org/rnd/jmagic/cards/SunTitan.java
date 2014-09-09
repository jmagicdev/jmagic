package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sun Titan")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT})
@ManaCost("4WW")
@ColorIdentity({Color.WHITE})
public final class SunTitan extends Card
{
	public static final class SunTitanAbility1 extends EventTriggeredAbility
	{
		public SunTitanAbility1(GameState state)
		{
			super(state, "Whenever Sun Titan enters the battlefield or attacks, you may return target permanent card with converted mana cost 3 or less from your graveyard to the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addPattern(whenThisAttacks());

			SetGenerator permanentCards = HasType.instance(Type.permanentTypes());
			SetGenerator convertedManaCostThreeOrLess = HasConvertedManaCost.instance(Between.instance(null, 3));
			SetGenerator inYourGraveyard = InZone.instance(GraveyardOf.instance(You.instance()));
			SetGenerator targeting = Intersect.instance(permanentCards, convertedManaCostThreeOrLess, inYourGraveyard);

			SetGenerator target = targetedBy(this.addTarget(targeting, "target permanent card with converted mana cost 3 or less from your graveyard"));
			EventFactory returnToBattlefield = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Return target permanent card with converted mana cost 3 or less from your graveyard to the battlefield");
			returnToBattlefield.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnToBattlefield.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			returnToBattlefield.parameters.put(EventType.Parameter.OBJECT, target);
			this.addEffect(youMay(returnToBattlefield, "You may return target permanent card with converted mana cost 3 or less from your graveyard to the battlefield."));
		}
	}

	public SunTitan(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// Whenever Sun Titan enters the battlefield or attacks, you may return
		// target permanent card with converted mana cost 3 or less from your
		// graveyard to the battlefield.
		this.addAbility(new SunTitanAbility1(state));
	}
}
