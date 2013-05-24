package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stonecloaker")
@Types({Type.CREATURE})
@SubTypes({SubType.GARGOYLE})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.PLANAR_CHAOS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class Stonecloaker extends Card
{
	public static final class StonecloakerAbility2 extends EventTriggeredAbility
	{
		public StonecloakerAbility2(GameState state)
		{
			super(state, "When Stonecloaker enters the battlefield, return a creature you control to its owner's hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventType.ParameterMap bounceParameters = new EventType.ParameterMap();
			bounceParameters.put(EventType.Parameter.CAUSE, This.instance());
			bounceParameters.put(EventType.Parameter.PLAYER, You.instance());
			bounceParameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			bounceParameters.put(EventType.Parameter.CHOICE, Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.CREATURE)));
			this.addEffect(new EventFactory(EventType.PUT_INTO_HAND_CHOICE, bounceParameters, "Return a creature you control to its owner's hand."));
		}
	}

	public static final class StonecloakerAbility3 extends EventTriggeredAbility
	{
		public StonecloakerAbility3(GameState state)
		{
			super(state, "When Stonecloaker enters the battlefield, exile target card from a graveyard.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(InZone.instance(GraveyardOf.instance(Players.instance())), "target card from a graveyard"));
			this.addEffect(exile(target, "Exile target card from a graveyard."));
		}
	}

	public Stonecloaker(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Stonecloaker enters the battlefield, return a creature you
		// control to its owner's hand.
		this.addAbility(new StonecloakerAbility2(state));

		// When Stonecloaker enters the battlefield, exile target card from a
		// graveyard.
		this.addAbility(new StonecloakerAbility3(state));
	}
}
