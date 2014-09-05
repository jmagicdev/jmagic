package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Crown of Empires")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class CrownofEmpires extends Card
{
	public static final class CrownofEmpiresAbility0 extends ActivatedAbility
	{
		public CrownofEmpiresAbility0(GameState state)
		{
			super(state, "(3), (T): Tap target creature. Gain control of that creature instead if you control artifacts named Scepter of Empires and Throne of Empires.");
			this.setManaCost(new ManaPool("(3)"));
			this.costsTap = true;

			SetGenerator scepter = Intersect.instance(ArtifactPermanents.instance(), HasName.instance("Scepter of Empires"));
			SetGenerator haveScepter = Intersect.instance(ControlledBy.instance(You.instance()), scepter);
			SetGenerator throne = Intersect.instance(ArtifactPermanents.instance(), HasName.instance("Throne of Empires"));
			SetGenerator haveThrone = Intersect.instance(ControlledBy.instance(You.instance()), throne);
			SetGenerator masterCollector = Both.instance(haveScepter, haveThrone);

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			EventFactory tap = tap(target, "Tap target creature");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			EventFactory gainControl = createFloatingEffect("Gain control of target creature", part);
			gainControl.parameters.put(EventType.Parameter.EXPIRES, Empty.instance());

			this.addEffect(ifThenElse(masterCollector, gainControl, tap, "Gain control of that creature instead if you control artifacts named Scepter of Empires and Throne of Empires."));
		}
	}

	public CrownofEmpires(GameState state)
	{
		super(state);

		// (3), (T): Tap target creature. Gain control of that creature instead
		// if you control artifacts named Scepter of Empires and Throne of
		// Empires.
		this.addAbility(new CrownofEmpiresAbility0(state));
	}
}
