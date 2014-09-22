package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Detention Sphere")
@Types({Type.ENCHANTMENT})
@ManaCost("1WU")
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class DetentionSphere extends Card
{
	public static final class DetentionSphereAbility0 extends EventTriggeredAbility
	{
		public DetentionSphereAbility0(GameState state)
		{
			super(state, "When Detention Sphere enters the battlefield, you may exile target nonland permanent not named Detention Sphere and all other permanents with the same name as that permanent.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator nonLandPermanents = RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND));
			SetGenerator notNamedThis = RelativeComplement.instance(nonLandPermanents, HasName.instance("Detention Sphere"));
			SetGenerator target = targetedBy(this.addTarget(notNamedThis, "target nonland permanent not named Detention Sphere"));

			SetGenerator hasSameName = Intersect.instance(Permanents.instance(), HasName.instance(NameOf.instance(target)));
			SetGenerator whatToExile = Union.instance(target, RelativeComplement.instance(hasSameName, target));
			EventFactory exile = exile(whatToExile, "Exile target nonland permanent not named Detention Sphere and all other permanents with the same name as that permanent.");
			exile.setLink(this);
			this.addEffect(exile);

			this.getLinkManager().addLinkClass(DetentionSphereAbility1.class);
		}
	}

	public static final class DetentionSphereAbility1 extends EventTriggeredAbility
	{
		public DetentionSphereAbility1(GameState state)
		{
			super(state, "When Detention Sphere leaves the battlefield, return the exiled cards to the battlefield under their owner's control.");
			this.addPattern(whenThisLeavesTheBattlefield());

			SetGenerator exiledCard = ChosenFor.instance(LinkedTo.instance(This.instance()));

			EventFactory returnCard = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_UNDER_OWNER_CONTROL, "Return the exiled cards to the battlefield under their owner's control.");
			returnCard.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnCard.parameters.put(EventType.Parameter.OBJECT, exiledCard);
			this.addEffect(returnCard);

			this.getLinkManager().addLinkClass(DetentionSphereAbility0.class);
		}
	}

	public DetentionSphere(GameState state)
	{
		super(state);

		// When Detention Sphere enters the battlefield, you may exile target
		// nonland permanent not named Detention Sphere and all other permanents
		// with the same name as that permanent.
		this.addAbility(new DetentionSphereAbility0(state));

		// When Detention Sphere leaves the battlefield, return the exiled cards
		// to the battlefield under their owner's control.
		this.addAbility(new DetentionSphereAbility1(state));
	}
}
