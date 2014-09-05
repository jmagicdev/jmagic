package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Razor Hippogriff")
@Types({Type.CREATURE})
@SubTypes({SubType.HIPPOGRIFF})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class RazorHippogriff extends Card
{
	public static final class RazorHippogriffAbility1 extends EventTriggeredAbility
	{
		public RazorHippogriffAbility1(GameState state)
		{
			super(state, "When Razor Hippogriff enters the battlefield, return target artifact card from your graveyard to your hand. You gain life equal to that card's converted mana cost.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.ARTIFACT), InZone.instance(GraveyardOf.instance(You.instance()))), "target artifact card in your graveyard"));

			EventFactory move = new EventFactory(EventType.MOVE_OBJECTS, "Return target artifact card from your graveyard to your hand.");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			move.parameters.put(EventType.Parameter.OBJECT, target);
			this.addEffect(move);

			this.addEffect(gainLife(You.instance(), ConvertedManaCostOf.instance(target), "You gain life equal to that card's converted mana cost."));
		}
	}

	public RazorHippogriff(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Razor Hippogriff enters the battlefield, return target artifact
		// card from your graveyard to your hand. You gain life equal to that
		// card's converted mana cost.
		this.addAbility(new RazorHippogriffAbility1(state));
	}
}
