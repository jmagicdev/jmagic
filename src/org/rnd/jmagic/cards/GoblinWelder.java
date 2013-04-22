package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin Welder")
@Types({Type.CREATURE})
@SubTypes({SubType.ARTIFICER, SubType.GOBLIN})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.URZAS_LEGACY, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class GoblinWelder extends Card
{
	public static final class TargetsAreLegal extends SetGenerator
	{
		private SetGenerator targets;

		private TargetsAreLegal(SetGenerator targets)
		{
			this.targets = targets;
		}

		public static SetGenerator instance(SetGenerator targets)
		{
			return new TargetsAreLegal(targets);
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			for(Target t: this.targets.evaluate(state, thisObject).getAll(Target.class))
				if(!t.isLegal(state.game, (GameObject)thisObject))
					return Empty.set;
			return NonEmpty.set;
		}
	}

	public static final class GoblinWelderAbility0 extends ActivatedAbility
	{
		public GoblinWelderAbility0(GameState state)
		{
			super(state, "(T): Choose target artifact a player controls and target artifact card in that player's graveyard. If both targets are still legal as this ability resolves, that player simultaneously sacrifices the artifact and returns the artifact card to the battlefield.");
			this.costsTap = true;

			SetGenerator artifactsInYards = Intersect.instance(HasType.instance(Type.ARTIFACT), InZone.instance(GraveyardOf.instance(Players.instance())));
			SetGenerator hasArtifactInYard = OwnerOf.instance(artifactsInYards);
			SetGenerator legalFirstTargets = Intersect.instance(ArtifactPermanents.instance(), ControlledBy.instance(hasArtifactInYard));
			Target target1 = this.addTarget(legalFirstTargets, "target artifact a player controls");
			SetGenerator targetArtifact = targetedBy(target1);

			SetGenerator thatPlayer = ControllerOf.instance(targetArtifact);
			SetGenerator legalSecondTargets = Intersect.instance(HasType.instance(Type.ARTIFACT), InZone.instance(GraveyardOf.instance(thatPlayer)));
			Target target2 = this.addTarget(legalSecondTargets, "target artifact card in that player's graveyard");
			SetGenerator targetArtifactCardInGraveyard = targetedBy(target2);

			EventFactory sacrifice = new EventFactory(EventType.SACRIFICE_PERMANENTS, "That player sacrifices the artifact");
			sacrifice.parameters.put(EventType.Parameter.CAUSE, This.instance());
			sacrifice.parameters.put(EventType.Parameter.PLAYER, thatPlayer);
			sacrifice.parameters.put(EventType.Parameter.PERMANENT, targetArtifact);

			EventFactory weld = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "That player returns the artifact card to the battlefield");
			weld.parameters.put(EventType.Parameter.CAUSE, This.instance());
			weld.parameters.put(EventType.Parameter.OBJECT, targetArtifactCardInGraveyard);
			weld.parameters.put(EventType.Parameter.CONTROLLER, thatPlayer);

			EventFactory effect = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If both targets are still legal as this ability resolves, that player simultaneously sacrifices the artifact and returns the artifact card to the battlefield.");
			effect.parameters.put(EventType.Parameter.IF, TargetsAreLegal.instance(AllTargetsOf.instance(This.instance())));
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(simultaneous(sacrifice, weld)));
			this.addEffect(effect);
		}
	}

	public GoblinWelder(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Choose target artifact a player controls and target artifact
		// card in that player's graveyard. If both targets are still legal as
		// this ability resolves, that player simultaneously sacrifices the
		// artifact and returns the artifact card to the battlefield.
		this.addAbility(new GoblinWelderAbility0(state));
	}
}
