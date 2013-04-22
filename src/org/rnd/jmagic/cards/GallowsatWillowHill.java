package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gallows at Willow Hill")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({})
public final class GallowsatWillowHill extends Card
{
	public static final class GallowsatWillowHillAbility0 extends ActivatedAbility
	{
		public GallowsatWillowHillAbility0(GameState state)
		{
			super(state, "(3), (T), Tap three untapped Humans you control: Destroy target creature. Its controller puts a 1/1 white Spirit creature token with flying onto the battlefield.");
			this.setManaCost(new ManaPool("(3)"));
			this.costsTap = true;

			// Tap three untapped Humans you control
			EventFactory cost = new EventFactory(EventType.TAP_CHOICE, "Tap three untapped Humans you control");
			cost.parameters.put(EventType.Parameter.CAUSE, This.instance());
			cost.parameters.put(EventType.Parameter.PLAYER, You.instance());
			cost.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.HUMAN)));
			cost.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
			this.addCost(cost);

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(destroy(target, "Destroy target creature."));

			CreateTokensFactory factory = new CreateTokensFactory(1, 1, 1, "Its controller puts a 1/1 white Spirit creature token with flying onto the battlefield.");
			factory.setController(ControllerOf.instance(target));
			factory.setColors(Color.WHITE);
			factory.setSubTypes(SubType.SPIRIT);
			factory.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(factory.getEventFactory());
		}
	}

	public GallowsatWillowHill(GameState state)
	{
		super(state);

		// (3), (T), Tap three untapped Humans you control: Destroy target
		// creature. Its controller puts a 1/1 white Spirit creature token with
		// flying onto the battlefield.
		this.addAbility(new GallowsatWillowHillAbility0(state));
	}
}
