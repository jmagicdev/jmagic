package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Clock of Omens")
@Types({Type.ARTIFACT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.FIFTH_DAWN, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class ClockofOmens extends Card
{
	public static final class TapTapTickTock extends ActivatedAbility
	{
		public TapTapTickTock(GameState state)
		{
			super(state, "Tap two untapped artifacts you control: Untap target artifact.");

			SetGenerator yourArtifacts = Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.ARTIFACT));
			SetGenerator yourUntappedArtifacts = Intersect.instance(Untapped.instance(), yourArtifacts);

			EventFactory tapTwoArtifacts = new EventFactory(EventType.TAP_CHOICE, "Tap two untapped artifacts you control");
			tapTwoArtifacts.parameters.put(EventType.Parameter.CAUSE, This.instance());
			tapTwoArtifacts.parameters.put(EventType.Parameter.PLAYER, You.instance());
			tapTwoArtifacts.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			tapTwoArtifacts.parameters.put(EventType.Parameter.CHOICE, yourUntappedArtifacts);
			this.addCost(tapTwoArtifacts);

			Target target = this.addTarget(ArtifactPermanents.instance(), "target artifact");
			this.addEffect(untap(targetedBy(target), "Untap target artifact."));
		}
	}

	public ClockofOmens(GameState state)
	{
		super(state);

		// Tap two untapped artifacts you control: Untap target artifact.
		this.addAbility(new TapTapTickTock(state));
	}
}
