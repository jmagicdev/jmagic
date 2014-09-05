package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Courser of Kruphix")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.CENTAUR})
@ManaCost("1GG")
@Printings({@Printings.Printed(ex = BornOfTheGods.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class CourserofKruphix extends Card
{
	public static final class CourserofKruphixAbility1 extends StaticAbility
	{
		public CourserofKruphixAbility1(GameState state)
		{
			super(state, "You may play the top card of your library if it's a land card.");

			SetGenerator yourLibrary = LibraryOf.instance(You.instance());
			SetGenerator land = Intersect.instance(TopCards.instance(1, yourLibrary), HasType.instance(Type.LAND));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MAY_PLAY_LOCATION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, land);
			part.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(new PlayPermission(You.instance())));
			this.addEffectPart(part);
		}
	}

	public static final class CourserofKruphixAbility2 extends EventTriggeredAbility
	{
		public CourserofKruphixAbility2(GameState state)
		{
			super(state, "Whenever a land enters the battlefield under your control, you gain 1 life.");
			this.addPattern(landfall());
			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public CourserofKruphix(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Play with the top card of your library revealed.
		this.addAbility(new org.rnd.jmagic.abilities.RevealTopOfLibrary(state));

		// You may play the top card of your library if it's a land card.
		this.addAbility(new CourserofKruphixAbility1(state));

		// Whenever a land enters the battlefield under your control, you gain 1
		// life.
		this.addAbility(new CourserofKruphixAbility2(state));
	}
}
